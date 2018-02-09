package br.com.iandev.midiaindoor.wrap.runners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.core.interfaces.Playable;
import br.com.iandev.midiaindoor.model.Channel;
import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.model.Program;
import br.com.iandev.midiaindoor.model.ProgramContent;
import br.com.iandev.midiaindoor.model.Programming;
import br.com.iandev.midiaindoor.util.DateUtil;
import br.com.iandev.midiaindoor.wrap.facede.ChannelFacede;
import br.com.iandev.midiaindoor.wrap.facede.ContentFacede;
import br.com.iandev.midiaindoor.wrap.facede.DeviceFacede;
import br.com.iandev.midiaindoor.wrap.facede.ProgramContentFacede;
import br.com.iandev.midiaindoor.wrap.facede.ProgramFacede;
import br.com.iandev.midiaindoor.wrap.facede.ProgrammingFacede;

/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public class Player extends RunnableWrapper<Playable> {
    private final DeviceFacede deviceController;
    private final ChannelFacede channelFacede;
    private final ContentFacede contentFacede;
    private final ProgramFacede programController;
    private final ProgrammingFacede programmingController;
    private final ProgramContentFacede programContentController;


    private List<Playable> playableList;

    public Player(App app) {
        super(app);

        this.deviceController = new DeviceFacede(super.getApp());
        this.channelFacede = new ChannelFacede(super.getApp());
        this.contentFacede = new ContentFacede(super.getApp());
        this.programController = new ProgramFacede(super.getApp());
        this.programmingController = new ProgrammingFacede(super.getApp());
        this.programContentController = new ProgramContentFacede(super.getApp());
    }

    public static List<Playable> getPlayableList(Date date, TimeZone timeZone, Device device, List<Channel> channelList, List<Program> programList, List<Programming> programmingList, List<ProgramContent> programContentList, List<Content> contentList) {
        List<Grade> gradeList = new ArrayList<>();

        if (!Character.valueOf('A').equals(device.getStatus())) {
            return null;
        }

        for (Channel channel : channelList) {
            if (!Character.valueOf('A').equals(channel.getStatus())) {
                continue;
            }

            if (!device.getChannel().getId().equals(channel.getId())) {
                continue;
            }

            for (Programming programming : programmingList) {
                if (!programming.getChannel().getId().equals(channel.getId())) {
                    continue;
                }
                if (!programming.mustPlay(date, timeZone)) {
                    continue;
                }
                for (Program program : programList) {
                    if (!program.getId().equals(programming.getProgram().getId())) {
                        continue;
                    }
                    if (!Character.valueOf('A').equals(program.getStatus())) {
                        continue;
                    }

                    for (ProgramContent programContent : programContentList) {
                        if (!programContent.getProgram().getId().equals(program.getId())) {
                            continue;
                        }

                        for (Content content : contentList) {
                            if (!content.getId().equals(programContent.getContent().getId())) {
                                continue;
                            }
                            if (!content.canPlay(DateUtil.trunc(date, timeZone))) {
                                continue;
                            }
                            gradeList.add(new Grade(channel, program, programming, programContent, content));
                        }
                    }
                }
            }
        }

        Collections.sort(gradeList, new Comparator<Grade>() {
            @Override
            public int compare(Grade o1, Grade o2) {
                Long programId1 = o1.getProgram().getId();
                Long programId2 = o2.getProgram().getId();
                if (!programId1.equals(programId2)) {
                    return programId1.compareTo(programId2);
                }

                Integer sequence1 = o1.getProgramContent().getSequence();
                Integer sequence2 = o2.getProgramContent().getSequence();
                return sequence1.compareTo(sequence2);
            }
        });

        List<Playable> playableList = new ArrayList<>();
        for (Grade grade : gradeList) {
            playableList.add(grade.getContent());
        }

        return playableList;
    }

    @Override
    protected synchronized final void start() {
        try {
            App app = super.getApp();
            if (playableList == null || playableList.size() == 0) {
                Device device = deviceController.get();
                playableList = getPlayableList(
                        app.getTimeCounter().getDate(),
                        device.getTimeZone(), device,
                        channelFacede.list(),
                        programController.list(),
                        programmingController.list(),
                        programContentController.list(),
                        contentFacede.list());
            }
            Content content = contentFacede.get((Content) playableList.remove(0));
            content.setLastPlaybackDate(app.getTimeCounter().getDate());
            contentFacede.set(content);
            super.success(content);
        } catch (Exception ex) {
            super.error(ex);
        }
    }

    public String getTag() {
        return "Player";
    }

    static private class Grade {
        private final Channel channel;
        private final Programming programming;
        private final Program program;
        private final ProgramContent programContent;
        private final Content content;

        Grade(Channel channel, Program program, Programming programming, ProgramContent programContent, Content content) {
            this.channel = channel;
            this.program = program;
            this.programming = programming;
            this.programContent = programContent;
            this.content = content;
        }

        public Channel getChannel() {
            return channel;
        }

        public Program getProgram() {
            return program;
        }

        public Programming getProgramming() {
            return programming;
        }

        public ProgramContent getProgramContent() {
            return programContent;
        }

        public Content getContent() {
            return content;
        }
    }
}
