package br.com.iandev.midiaindoor.view.item;

import android.view.View;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.Date;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.model.Channel;
import br.com.iandev.midiaindoor.model.Device;
import br.com.iandev.midiaindoor.model.Person;
import br.com.iandev.midiaindoor.util.ViewUtil;

/**
 * Created by Lucas on 18/04/2017.
 * Changes:
 * Date        Responsible     Change
 * 18/04/2017  Lucas
 */

public class DeviceItem extends Item {
    public DeviceItem(final View view, final App app, final Device device, final Channel channel, final Person person) {
        super(view);
        ((TextView) view.findViewById(R.id.id)).setText(ViewUtil.getString(device.getId()));
        ((TextView) view.findViewById(R.id.description)).setText(ViewUtil.getString(device.getDescription()));
        ((TextView) view.findViewById(R.id.status)).setText(ViewUtil.getString(device.getStatus()));

        ((TextView) view.findViewById(R.id.person)).setText(ViewUtil.getString(person));
        ((TextView) view.findViewById(R.id.channel)).setText(ViewUtil.getString(channel));
        ((TextView) view.findViewById(R.id.timeZone)).setText(ViewUtil.getString(device.getTimeZone()));

        ((TextView) view.findViewById(R.id.updateInterval)).setText(ViewUtil.getInterval(device.getUpdateInterval()));
        ((TextView) view.findViewById(R.id.updateToleranceInterval)).setText(ViewUtil.getInterval(device.getUpdateToleranceInterval()));
        ((TextView) view.findViewById(R.id.lastUpdateDate)).setText(ViewUtil.getString(device.getLastUpdateDate(), device.getTimeZone()));
        ((TextView) view.findViewById(R.id.lastUpdateAttemptDate)).setText(ViewUtil.getString(device.getLastUpdateAttemptDate(), device.getTimeZone()));

        Date nextUpdateDate = null;

        try {
            nextUpdateDate = device.getNextUpdateDate();
        } catch (Exception e) {
            // Silence is golden
        }
        ((TextView) view.findViewById(R.id.nextUpdateDate)).setText(ViewUtil.getString(nextUpdateDate, device.getTimeZone()));
        ((CheckedTextView) view.findViewById(R.id.mustUpdate)).setChecked(device.mustUpdate(app.getTimeCounter().getDate()));
        ((CheckedTextView) view.findViewById(R.id.outOfDate)).setChecked(device.outOfDate(app.getTimeCounter().getDate()));

        ((TextView) view.findViewById(R.id.lastAuthenticationDate)).setText(ViewUtil.getString(device.getLastAuthenticationDate(), device.getTimeZone()));
        ((TextView) view.findViewById(R.id.tokenExpirationInterval)).setText(ViewUtil.getInterval(device.getTokenExpirationInterval()));
        ((TextView) view.findViewById(R.id.token)).setText(ViewUtil.getString(device.getToken()));
    }
}
