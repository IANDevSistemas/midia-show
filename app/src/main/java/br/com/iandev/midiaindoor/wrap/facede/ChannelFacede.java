package br.com.iandev.midiaindoor.wrap.facede;

import java.util.List;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.dao.ChannelDao;
import br.com.iandev.midiaindoor.model.Channel;

/**
 * Created by Lucas on 31/03/2017.
 * Changes:
 * Date        Responsible     Change
 * 31/03/2017  Lucas
 */

public class ChannelFacede extends Facede<Channel> {
    private static List<Channel> cache;

    public ChannelFacede(App app) {
        super(app, new ChannelDao(app.getContext()));
    }

    @Override
    protected List<Channel> getCache() {
        return cache;
    }

    @Override
    protected void setCache(List<Channel> cache) {
        ChannelFacede.cache = cache;
    }
}
