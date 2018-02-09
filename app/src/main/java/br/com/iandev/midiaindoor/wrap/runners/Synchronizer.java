package br.com.iandev.midiaindoor.wrap.runners;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.core.TimeCounter;
import br.com.iandev.midiaindoor.model.Channel;
import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.model.Program;
import br.com.iandev.midiaindoor.model.ProgramContent;
import br.com.iandev.midiaindoor.model.Programming;
import br.com.iandev.midiaindoor.util.JSONUtil;
import br.com.iandev.midiaindoor.wrap.facede.ChannelFacede;
import br.com.iandev.midiaindoor.wrap.facede.ContentFacede;
import br.com.iandev.midiaindoor.wrap.facede.DeviceFacede;
import br.com.iandev.midiaindoor.wrap.facede.PersonFacede;
import br.com.iandev.midiaindoor.wrap.facede.ProgramContentFacede;
import br.com.iandev.midiaindoor.wrap.facede.ProgramFacede;
import br.com.iandev.midiaindoor.wrap.facede.ProgrammingFacede;
import br.com.iandev.midiaindoor.wrap.settings.LicenseSettings;

/**
 * Created by Lucas on 29/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 29/03/2017  Lucas
 */

public class Synchronizer extends IntegrationRunnable<Device> {
    private final LicenseSettings licenseSettings;
    private final DeviceFacede deviceController;
    private final PersonFacede personController;
    private final ContentFacede contentFacede;
    private final ChannelFacede channelFacede;
    private final ProgrammingFacede programmingController;
    private final ProgramFacede programController;
    private final ProgramContentFacede programContentController;

    public Synchronizer(App app) {
        super(app);
        this.licenseSettings = new LicenseSettings(app);
        this.deviceController = new DeviceFacede(app);
        this.personController = new PersonFacede(app);
        this.contentFacede = new ContentFacede(app);
        this.channelFacede = new ChannelFacede(app);
        this.programmingController = new ProgrammingFacede(app);
        this.programController = new ProgramFacede(app);
        this.programContentController = new ProgramContentFacede(app);
    }

    @Override
    public final void start() {
        try {
            super.setUp();
            App app = super.getApp();

            Device device = deviceController.get();
            device.setLastUpdateAttemptDate(app.getTimeCounter().getDate());
            deviceController.set(device);

            br.com.iandev.midiaindoor.core.integration.bdo.Synchronizer synchronizer = new br.com.iandev.midiaindoor.core.integration.bdo.Synchronizer();
            JSONObject jsonObject = synchronizer.doSync();

            Date date = JSONUtil.getDate(jsonObject, "date");

            app.setTimeCounter(new TimeCounter(date));

            device = new Device(null).parse(jsonObject.getJSONObject("device"));
            device.setLastUpdateDate(app.getTimeCounter().getDate());
            deviceController.set(device);
            licenseSettings.setDeviceDescription(device.getDescription());

            personController.set(new Person(null).parse(jsonObject.getJSONObject("person")));

            List<Content> contentList = new Content(null).parseList(jsonObject.getJSONArray("content"));
            contentFacede.set(contentList);
            for (Content entity : contentFacede.list()) {
                boolean found = false;
                for (Content content : contentList) {
                    if (entity.getId().equals(content.getId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    contentFacede.delete(entity);
                }
            }

            channelFacede.delete();
            channelFacede.set(new Channel(null).parseList(jsonObject.getJSONArray("channel")));

            programmingController.delete();
            programmingController.set(new Programming(null).parseList(jsonObject.getJSONArray("programming")));

            programController.delete();
            programController.set(new Program(null).parseList(jsonObject.getJSONArray("program")));

            programContentController.delete();
            programContentController.set(new ProgramContent(null).parseList(jsonObject.getJSONArray("programContent")));

            super.success(deviceController.get());
        } catch (Exception ex) {
            super.error(ex);
        }
    }

    public String getTag() {
        return "Synchronizer";
    }
}
