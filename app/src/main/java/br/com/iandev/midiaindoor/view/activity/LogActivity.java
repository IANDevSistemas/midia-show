package br.com.iandev.midiaindoor.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import br.com.iandev.midiaindoor.R;
import br.com.iandev.midiaindoor.view.TitleBar;
import br.com.iandev.midiaindoor.wrap.log.Logger;

/**
 * Created by Lucas on 28/12/2016.
 * Changes:
 * Date        Responsible     Change
 * 28/12/2016  Lucas
 */

public class LogActivity extends AutoRefreshActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        new TitleBar(this, findViewById(R.id.title_bar), getResources().getString(R.string.title_log));

//        timeZone = TimeZone.getDefault();
//        App app = App.getInstance();
//        try {
//            timeZone = new DeviceFacede(app).get().getTimeZone();
//        } catch (Exception ex) {
//            // Silence is golden
//        }

        super.autoRefresh();
    }

    // TODO: fazer código mais bonito
    // TODO: Logs com horario do sincronismo
    @Override
    protected void refresh() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                final File file = new File(Logger.FILENAME);
                final StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView output = (TextView) findViewById(R.id.log);
                            output.setText(text);
                        }
                    });
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), getResources().getText(R.string.message_error), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }


//    private void refreshList() {
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                List<Log> logList = null;
//                try {
//                    logList = new LogFacede(App.getInstance()).list();
//                } catch (NotFoundException e) {
//                }
//                ListView listView = (ListView) findViewById(R.id.log_list);
//                ListAdapter adapter = new ListAdapter(getActivity(), logList);
//                listView.setAdapter(adapter);
//            }
//        });
//    }
//
//
//    private Activity getActivity() {
//        return this;
//    }
//
//    private class ListAdapter extends BaseAdapter {
//        final Activity activity;
//        final List<Log> list;
//
//        ListAdapter(Activity activity, List<Log> list) {
//            Collections.sort(list, new Comparator<Log>() {
//                @Override
//                public int compare(Log o1, Log o2) {
//                    return o2.getId().compareTo(o1.getId());
//                }
//            });
//            this.activity = activity;
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return list.get(position).getId();
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = this.activity.getLayoutInflater().inflate(R.layout.item_log, (ViewGroup) null);
//
//            Log log = list.get(position);
//
//            ((TextView) view.findViewById(R.id.id)).setText("#" + log.getId().toString());
//            ((TextView) view.findViewById(R.id.date)).setText(log.getDate() != null ? DateUtil.fullFormat(log.getDate(), timeZone) : "");
//            ((TextView) view.findViewById(R.id.tag)).setText(log.getTag() != null ? log.getTag() : "");
//            try {
//                ((TextView) view.findViewById(R.id.event)).setText(getResources().getString(log.getEvent()));
//            } catch (Exception ex) {
//                // Silence is golden
//            }
//
//            try {
//                ((TextView) view.findViewById(R.id.message)).setText(log.getMessage() != null ? getResources().getString(log.getMessage()) : "");
//            } catch (Exception ex) {
//                // Silence is golden
//            }
//
//            ((TextView) view.findViewById(R.id.json)).setText(log.getJson() != null ? log.getJson() : "");
//
//            return view;
//        }
//    }
}

