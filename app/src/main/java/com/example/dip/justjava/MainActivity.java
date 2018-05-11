/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.dip.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText get_name = findViewById(R.id.Customer_name_view);
        String name =get_name.getText().toString();
        int price =calculatePrice(hasWhippedCream,hasChocolate);
        String orderSummary=createOrderSummary(price,hasWhippedCream,hasChocolate,name);


    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean hasWhippedCream , boolean hasChocolate) {

        int basePrice = 5;
        if (hasWhippedCream){
            basePrice = basePrice + 1;
        }

        if (hasChocolate){
            basePrice = basePrice + 2;
        }

        int price = quantity * basePrice;
        return  price;
    }

    public String createOrderSummary(int price,boolean addWhippedCream ,boolean addChocolate, String name){
        String priceMessage=getString(R.string.order_summary_name,name) ;
        priceMessage += "\n" + getString(R.string.order_summary_Whipped_cream,addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_Chocolate,addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_Quantity,quantity);
         priceMessage += "\n" + getString(R.string.order_summary_Price,price);
        priceMessage += "\n" + getString(R.string.thank_you);


            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_Subject,name) );
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        return priceMessage;


    }

    public void increment(View view) {
        if (quantity==100){
            Toast.makeText(this, "Can't have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity=quantity+1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity==1){
            Toast.makeText(this, "Can't have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.quantity_text_view);
        orderSummaryTextView.setText(Integer.toString(number));
    }

}