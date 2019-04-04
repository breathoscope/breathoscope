package org.inspire.breath.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.inspire.breath.R;
import org.inspire.breath.data.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter {

    public interface PatientCallback {
        void onSelected(Patient patient);
    }

    public class PatientListViewHolder extends RecyclerView.ViewHolder {

        private TextView mText;
        private ImageView mProfile;
        private Patient mPatient;

        public int id;

        public PatientListViewHolder(@NonNull CardView root, final Patient patient, final PatientCallback callback) {
            super(root);
            mText = root.findViewById(R.id.patient_list_holder_name);
            mProfile = root.findViewById(R.id.patient_list_holder_thumb);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onSelected(mPatient);
                }
            });
            this.setPatient(patient);
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPatient(Patient patient) {
            this.mPatient = patient;
            this.mText.setText(mPatient.getFirstName() + ' ' + mPatient.getLastName());
            byte[] bmp = mPatient.getThumb();
            if (bmp != null && bmp.length > 0)
                this.mProfile.setImageBitmap(BitmapFactory.decodeByteArray(bmp,0,bmp.length));
        }

    }


    private List<Patient> mPatients;
    private PatientCallback mCallback;

    public void updatePatients(List<Patient> newPatients) {
        for (Patient patient: newPatients) {
            if (!mPatients.contains(patient)) {
                mPatients.add(patient);
            }
        }
        this.notifyDataSetChanged();
    }

    public void setPatients(List<Patient> newPatients) {
        this.mPatients = newPatients;
        this.notifyDataSetChanged();
    }

    public void clearPatients(List<Patient> newPatients) {
        this.mPatients = new ArrayList<>();
        this.notifyDataSetChanged();
    }

    public PatientListAdapter(List<Patient> patients, PatientCallback callback) {
        this.mCallback = callback;
        mPatients = patients;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        CardView rootView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_list_holder, parent, false);
        return new PatientListViewHolder(rootView, mPatients.get(i), mCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PatientListViewHolder holder = (PatientListViewHolder) viewHolder;
        holder.setId(i);
        holder.setPatient(mPatients.get(i));
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }
}
