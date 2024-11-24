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

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Find views
        EditText edtEmail = view.findViewById(R.id.email);
        EditText edtPassword = view.findViewById(R.id.password);
        Button btnRegisterAccount = view.findViewById(R.id.register_button);
        Button returnGotoLogin = view.findViewById(R.id.goto_login_screen);

        // Set up button click listener for register button
        btnRegisterAccount.setOnClickListener(new OnClickListener(edtEmail, edtPassword));

        // Set up button click listener for go to login button
        returnGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new LoginFragment());
            }
        });

        return view;
    }

    // OnClickListener class for handling register button click
    private class OnClickListener implements View.OnClickListener {
        private final EditText edtEmail;
        private final EditText edtPassword;

        // Constructor to receive the views
        public OnClickListener(EditText edtEmail, EditText edtPassword) {
            this.edtEmail = edtEmail;
            this.edtPassword = edtPassword;
        }

        @Override
        public void onClick(View view) {
            String email = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            // Check if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please fill out all information completely!");
            } else {
                // If all fields are filled, show registration success message
                showToast("Registered successfully!");
            }
        }
    }

    // Helper method to load fragment
    private void loadFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    // Helper method to show custom toast message
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

    }
}
