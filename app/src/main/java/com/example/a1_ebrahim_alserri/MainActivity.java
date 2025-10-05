package com.example.a1_ebrahim_alserri;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    public static java.util.ArrayList<String> paymentRows = new java.util.ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        Button calculate = findViewById(R.id.calculate);
        EditText hours = findViewById(R.id.hours);
        EditText rate = findViewById(R.id.rate);
        TextView pay = findViewById(R.id.pay);
        TextView overtime = findViewById(R.id.overtime);
        TextView tax = findViewById(R.id.tax);
        TextView total = findViewById(R.id.total);

        calculate.setOnClickListener(v -> {
            String hours_Str = hours.getText().toString().trim();
            String rate_Str = rate.getText().toString().trim();

            if (hours_Str.isEmpty() || rate_Str.isEmpty()) {
                Toast.makeText(this, "Enter hours and rate", Toast.LENGTH_SHORT).show();
                return;
            }

            double hours_Val = Double.parseDouble(hours_Str);
            double rate_Val = Double.parseDouble(rate_Str);

            if (hours_Val < 0 || rate_Val < 0) {
                Toast.makeText(this, "Values must be non-negative", Toast.LENGTH_SHORT).show();
                return;
            }

            double pay_Val;
            double overtime_Val = 0;
            if (hours_Val <= 40) {
                pay_Val = hours_Val * rate_Val;
            } else {
                overtime_Val = (hours_Val - 40) * rate_Val * 0.5;
                pay_Val = 40 * rate_Val + (hours_Val - 40) * rate_Val * 1.5;
            }
            double tax_Val = pay_Val * 0.18;
            double total_Val = pay_Val - tax_Val;

            pay.setText(String.format("Pay: %.2f", pay_Val));
            overtime.setText(String.format("Overtime: %.2f", overtime_Val));
            tax.setText(String.format("Tax (18%%): %.2f", tax_Val));
            total.setText(String.format("Total after tax: %.2f", total_Val));

            String row = String.format(
                    "Hrs: %.2f  Rate: %.2f  Pay: %.2f  Overtime: %.2f  Tax: %.2f Total: %.2f",
                    hours_Val, rate_Val, pay_Val, overtime_Val, tax_Val, total_Val);
            paymentRows.add(row);

            Toast.makeText(this, "Calculated", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_view_payments){
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}