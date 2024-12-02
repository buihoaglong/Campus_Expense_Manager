package com.btec.fpt.campus_expense_manager.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.btec.fpt.campus_expense_manager.R;
import com.btec.fpt.campus_expense_manager.database.DatabaseHelper;
import com.btec.fpt.campus_expense_manager.entities.Transaction;

import java.util.ArrayList;

public class DisplayExpenseFragment extends Fragment {
    public DisplayExpenseFragment() {
    }

    private DatabaseHelper dbHelper;
    private ListView expensesListView;
    private Button clearHistoryButton;

    private ArrayList<Transaction> transactionList;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_expense, container, false);
        dbHelper = new DatabaseHelper(getContext());

        expensesListView = view.findViewById(R.id.expensesListView);
        clearHistoryButton = view.findViewById(R.id.clearHistoryButton);

        loadExpenses();

        // Long click listener for updating and deleting transactions
        expensesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Transaction selectedTransaction = transactionList.get(position);

                // Show options to Update or Delete
                new AlertDialog.Builder(getContext())
                        .setTitle("Choose Action")
                        .setMessage("Would you like to update or delete this transaction?")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateTransaction(selectedTransaction);
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTransaction(selectedTransaction);
                            }
                        })
                        .setNeutralButton("Cancel", null)
                        .show();
                return true;
            }
        });

        clearHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearHistory();
            }
        });

        return view;
    }

    private void loadExpenses() {
        transactionList = (ArrayList<Transaction>) dbHelper.getTransactionList();
        ArrayList<String> expenseDescriptions = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            String description = transaction.getDate() + " - " + transaction.getDescription() + ": $" + transaction.getAmount();
            expenseDescriptions.add(description);
        }

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, expenseDescriptions);
        expensesListView.setAdapter(adapter);
    }

    private void updateTransaction(final Transaction transaction) {
        // Open a dialog to allow the user to update the transaction details
        // For now, just show a Toast for simplicity
        Toast.makeText(getContext(), "Updating transaction: " + transaction.getDescription(), Toast.LENGTH_SHORT).show();

        // Call dbHelper.updateTransaction(updatedTransaction) here after the user makes changes
    }

    private void deleteTransaction(final Transaction transaction) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Transaction")
                .setMessage("Are you sure you want to delete this transaction?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteTransaction(transaction.getId());
                        loadExpenses(); // Reload the list after deletion
                        Toast.makeText(getContext(), "Transaction deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clearHistory() {
        new AlertDialog.Builder(getContext())
                .setTitle("Clear History")
                .setMessage("Are you sure you want to clear all transaction history?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.clearAllTransactions();
                        loadExpenses(); // Reload the list after clearing history
                        Toast.makeText(getContext(), "All transactions cleared", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
