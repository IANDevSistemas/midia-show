package br.com.iandev.midiaindoor.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.TimeZone;

import br.com.iandev.midiaindoor.App;
import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.model.Content;
import br.com.iandev.midiaindoor.util.ViewUtil;
import br.com.iandev.midiaindoor.view.TitleBar;
import br.com.iandev.midiaindoor.wrap.facede.ContentFacede;
import br.com.iandev.midiaindoor.wrap.facede.DeviceFacede;
import br.com.iandev.midiaindoor.wrap.facede.NotFoundException;
import br.com.iandev.midiaindoor.wrap.runners.Manager;

/**
 * Created by Lucas on 30/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 30/12/2016  Lucas
 */

public class ContentActivity extends BaseActivity {
    private DeviceFacede deviceController;
    private ContentFacede contentFacede;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_service);

        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_content));

        this.deviceController = new DeviceFacede(App.getInstance());
        this.contentFacede = new ContentFacede(App.getInstance());
        this.adapter = new ListAdapter();
        refreshList();
    }

    private void refreshList() {
        new Thread() {
            @Override
            public void run() {
                try {
                    adapter.setDate(App.getInstance().getTimeCounter().getDate());
                } catch (Exception ex) {
                    adapter.setDate(new Date());
                }

                try {
                    adapter.setTimeZone(deviceController.get().getTimeZone());
                } catch (NotFoundException ex) {
                    adapter.setTimeZone(TimeZone.getDefault());
                }

                try {
                    adapter.setList(contentFacede.list());
                } catch (NotFoundException ex) {
                    adapter.setList(null);
                }

                ListView listView = (ListView) findViewById(R.id.list);
                listView.setAdapter(adapter);
            }
        }.start();
    }

    private Context getContext() {
        return this;
    }

    private Activity getActivity() {
        return this;
    }


    private class ListAdapter extends br.com.iandev.midiaindoor.view.ListAdapter<Content> {
        private TimeZone timeZone;
        private Date date;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = ContentActivity.this.getLayoutInflater().inflate(R.layout.item_content, null);

            final Content content = super.getItem(position);

            ((TextView) view.findViewById(R.id.id)).setText(ViewUtil.getString(content.getId()));
            ((TextView) view.findViewById(R.id.description)).setText(ViewUtil.getString(content.getDescription()));
            ((TextView) view.findViewById(R.id.status)).setText(ViewUtil.getString(content.getStatus()));

            ((TextView) view.findViewById(R.id.durationInterval)).setText(ViewUtil.getInterval(content.getDurationInterval()));
            ((TextView) view.findViewById(R.id.lastPlaybackDate)).setText(ViewUtil.getString(content.getLastPlaybackDate(), this.getTimeZone()));

            ((TextView) view.findViewById(R.id.updateInterval)).setText(ViewUtil.getInterval(content.getUpdateInterval()));
            ((TextView) view.findViewById(R.id.updateToleranceInterval)).setText(ViewUtil.getInterval(content.getUpdateToleranceInterval()));
            ((TextView) view.findViewById(R.id.lastUpdateDate)).setText(ViewUtil.getString(content.getLastUpdateDate(), this.getTimeZone()));
            ((TextView) view.findViewById(R.id.lastUpdateAttemptDate)).setText(ViewUtil.getString(content.getLastUpdateAttemptDate(), this.getTimeZone()));

            Date nextUpdateDate = null;

            try {
                nextUpdateDate = content.getNextUpdateDate();
            } catch (Exception ex) {
                // Silence is golden
            }

            ((TextView) view.findViewById(R.id.nextUpdateDate)).setText(ViewUtil.getString(nextUpdateDate, this.getTimeZone()));
            ((CheckedTextView) view.findViewById(R.id.mustUpdate)).setChecked(content.mustUpdate(this.getDate()));
            ((CheckedTextView) view.findViewById(R.id.outOfDate)).setChecked(content.outOfDate(this.getDate()));

            ((TextView) view.findViewById(R.id.startDate)).setText(ViewUtil.getString(content.getStartDate()));
            ((TextView) view.findViewById(R.id.endDate)).setText(ViewUtil.getString(content.getEndDate()));

            ((TextView) view.findViewById(R.id.integrationApi)).setText(ViewUtil.getString(content.getIntegrationApi()));
            ((TextView) view.findViewById(R.id.accessUrl)).setText(ViewUtil.getString(content.getAccessUrl()));
            ((TextView) view.findViewById(R.id.hash)).setText(ViewUtil.getString(content.getHash()));
            ((TextView) view.findViewById(R.id.alias)).setText(ViewUtil.getString(content.getAlias()));
            ((TextView) view.findViewById(R.id.indexFilePath)).setText(ViewUtil.getString(content.getIndexFilePath()));

            Button button = (Button) view.findViewById(R.id.action_refresh);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    view.setEnabled(false);

                    Manager manager = new Manager(App.getInstance(), content) {
                        @Override
                        public void onSuccess(Content content) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), getResources().getString(R.string.message_success), Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onError(Exception ex) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    view.setEnabled(true);
                                    Toast.makeText(getContext(), getResources().getString(R.string.message_error), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    };

                    manager.setDownloadForced(true);

                    new Thread(manager).start();

                }
            });
            return view;
        }

        public TimeZone getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(TimeZone timeZone) {
            this.timeZone = timeZone;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
