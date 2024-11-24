package com.btec.fpt.campus_expense_manager.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.btec.fpt.campus_expense_manager.R;

public class ForgotPasswordFragment extends Fragment {

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        // Find views
        EditText edtEmail = view.findViewById(R.id.email); // Đảm bảo id đúng
        Button btnReset = view.findViewById(R.id.reset_password_button);

        // Set up the button click listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();

                // Check if the email field is empty
                if (email.isEmpty()) {
                    showToast("Please enter your email address!");
                } else {
                    // If email is provided, simulate sending reset email
                    sendResetPasswordEmail(email);
                }
            }
        });

        return view;
    }

    // Method to simulate sending reset password email
    private void sendResetPasswordEmail(String email) {
        // Here you would implement the actual logic to send the reset password email
        // For this example, we show a success message after simulating the process
        showToast("A password reset link has been sent to " + email);
    }

    // Helper method to show custom toast message
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
