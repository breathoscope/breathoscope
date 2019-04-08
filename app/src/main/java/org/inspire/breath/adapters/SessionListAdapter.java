package org.inspire.breath.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.Session;
import org.inspire.breath.data.blobs.RecommendActionsResult;

import java.text.DateFormat;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SessionListAdapter extends RecyclerView.Adapter {
    public class SessionViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView date;
        ImageView breathStatus;
        ImageView feverStatus;
        ImageView heartStatus;
        ImageView diarrhoeaStatus;
        ImageView malariaStatus;
        ImageView dangerStatus;

        public SessionViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView;
            findViews();
        }

        private void findViews() {
            date = root.findViewById(R.id.date);
            breathStatus = root.findViewById(R.id.breath_status);
            feverStatus = root.findViewById(R.id.fever_status);
            heartStatus = root.findViewById(R.id.heart_status);
            diarrhoeaStatus = root.findViewById(R.id.diarrhoea_status);
            malariaStatus = root.findViewById(R.id.malaria_status);
            dangerStatus = root.findViewById(R.id.danger_status);
        }

        public void bind(Session session) {
            RecommendActionsResult actions = session.getRecommendedActions();
            for (RecommendActionsResult.Test test : RecommendActionsResult.Test.values()) {
                RecommendActionsResult.Action action = actions.getAction(test);
                if (action != null) {
                    if (test.equals(RecommendActionsResult.Test.BREATH)) {
                        breathStatus.setImageResource(StatusSelector.breathDrawables[action.getSeverity()]);
                    } else if (test.equals(RecommendActionsResult.Test.FEVER)) {
                        feverStatus.setImageResource(StatusSelector.feverDrawables[action.getSeverity()]);
                    } else if (test.equals(RecommendActionsResult.Test.DANGER)) {
                        dangerStatus.setImageResource(StatusSelector.dangerDrawables[action.getSeverity()]);
                    } else if (test.equals(RecommendActionsResult.Test.DIARRHOEA)) {
                        diarrhoeaStatus.setImageResource(StatusSelector.diarrhoeaDrawables[action.getSeverity()]);
                    } else if (test.equals(RecommendActionsResult.Test.MALARIA)) {
                        malariaStatus.setImageResource(StatusSelector.malariaDrawables[action.getSeverity()]);
                    } else if (test.equals(RecommendActionsResult.Test.HEART)) {
                        heartStatus.setImageResource(StatusSelector.heartDrawables[action.getSeverity()]);
                    }
                }
            }
            String dateText = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.UK).format(new Date(session.getTimestamp()));
//             = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date(session.getTimestamp()));
            date.setText(dateText);
        }
    }

    public List<Session> mSessions;

    public SessionListAdapter(List<Session> sessions) {
        super();
        this.mSessions = sessions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SessionViewHolder viewHolder = new SessionViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.session_list_holder, viewGroup, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((SessionViewHolder) viewHolder).bind(mSessions.get(i));
    }

    public void setSessions(List<Session> sessions) {
        this.mSessions = sessions;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mSessions.size();
    }

    static class StatusSelector {
        static final int OK = 0;
        static final int MED = 1;
        static final int SEVERE = 2;
        static int[] heartDrawables = {R.drawable.ic_clipboard_pulse_outline_ok,
                R.drawable.ic_clipboard_pulse_outline_med,
                R.drawable.ic_clipboard_pulse_outline_severe};
        static int[] malariaDrawables = {R.drawable.ic_baseline_photo_camera_ok_24px,
                R.drawable.ic_baseline_photo_camera_med_24px,
                R.drawable.ic_baseline_photo_camera_severe_24px};
        static int[] dangerDrawables = {R.drawable.ic_round_warning_ok_24px,
                R.drawable.ic_round_warning_med_24px,
                R.drawable.ic_round_warning_severe_24px};
        static int[] diarrhoeaDrawables = {R.drawable.ic_intestine_ok,
                R.drawable.ic_intestine_med,
                R.drawable.ic_intestine_severe};
        static int[] breathDrawables = {R.drawable.ic_stethoscope_ok,
                R.drawable.ic_stethoscope_med,
                R.drawable.ic_stethoscope_severe};
        static int[] feverDrawables = {R.drawable.ic_thermometer_ok_alert,
                R.drawable.ic_thermometer_med_alert,
                R.drawable.ic_thermometer_severe_alert};
    }
}
