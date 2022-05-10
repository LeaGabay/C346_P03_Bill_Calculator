package sg.edu.rp.c346.id21025553.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Declaring the field variables
    TextView totalBill, eachPays, errorMessage;
    Button splitBtn, resetBtn;
    EditText amtInput, paxInput, discInput;
    ToggleButton svsBtn, gstBtn;
    RadioGroup rgPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking field variables to UI components in layout
        totalBill = findViewById(R.id.totalBill);
        eachPays = findViewById(R.id.eachPays);
        errorMessage = findViewById(R.id.errorMsg);

        splitBtn = findViewById(R.id.splitButton);
        resetBtn = findViewById(R.id.resetButton);

        amtInput = findViewById(R.id.amountEdit);
        paxInput = findViewById(R.id.paxEdit);
        discInput = findViewById(R.id.discountEdit);

        svsBtn = findViewById(R.id.svsButton);
        gstBtn = findViewById(R.id.gstButton);

        rgPayment = findViewById(R.id.radioGroupPayment);

        // Split Button
        splitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Code for the action
                if(!amtInput.getText().toString().equals("") && !paxInput.getText().toString().equals("")){
                    double newAmt = 0.0;
                    double amount = Double.parseDouble(amtInput.getText().toString());

                    // Error Message
                    String error = "";
                    boolean error_check = false;

                    if(!svsBtn.isChecked() && !gstBtn.isChecked()){
                        newAmt = amount;
                    } else if (svsBtn.isChecked() && !gstBtn.isChecked()){
                        newAmt = amount * 1.1;
                    } else if(!svsBtn.isChecked() && gstBtn.isChecked()){
                        newAmt = amount* 1.07;
                    } else {
                        newAmt = amount * 1.17;
                    }



                    // Discount

                    double doubleDiscInput = Double.parseDouble(discInput.getText().toString());

                    if(discInput.getText().toString().trim().length() != 0 && doubleDiscInput < 100){
                        newAmt *= 1 - doubleDiscInput / 100;
                    } else {
                        error += "Invalid discount value. Please re-enter a value between 0 to 100";
                        error_check = true;
                    }

                    int numPax = Integer.parseInt(paxInput.getText().toString());
                    String stringResponse = "";

                    // Checks Number of People
                    if(numPax > 1){
                        stringResponse = String.format("%s%.2f", "Each Pays: $", newAmt / numPax);
                    } else {
                        stringResponse = String.format("%s%.2f", "Each Pays: $", newAmt);


                        // Checks Payment Mode
                        int checkedRadioId = rgPayment.getCheckedRadioButtonId();

                        if (checkedRadioId == R.id.radioButtonPaymentCash) {
                            stringResponse += " in cash";
                        } else {
                            stringResponse += " via PayNow to 912345678";
                        }

                    }
                    if (error_check == true) {
                        errorMessage.setText(error);
                        Toast.makeText(MainActivity.this, "Invalid discount value", Toast.LENGTH_SHORT).show();
                        totalBill.setText("");
                        eachPays.setText("");

                    } else {
                        totalBill.setText(String.format("%s%.2f", "Total Bills: $", newAmt));
                        eachPays.setText(stringResponse);
                        Toast.makeText(MainActivity.this, "Split Button has been clicked", Toast.LENGTH_SHORT).show();
                        errorMessage.setText("");
                    }
                }

            }
        });

        // Reset Button
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amtInput.setText("");
                paxInput.setText("");
                discInput.setText("");
                rgPayment.check(R.id.radioButtonPaymentCash);
                svsBtn.setChecked(false);
                gstBtn.setChecked(false);
                totalBill.setText("");
                eachPays.setText("");
                errorMessage.setText("");
                Toast.makeText(MainActivity.this, "Reset Button has been clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }

}