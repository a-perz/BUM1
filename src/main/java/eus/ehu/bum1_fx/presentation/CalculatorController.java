package eus.ehu.bum1_fx.presentation;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import eus.ehu.bum1_fx.business_logic.BarcenaysCalculator;
import eus.ehu.bum1_fx.business_logic.ExchangeCalculator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class CalculatorController {

        @FXML
        private TextField amountTextField;

        @FXML
        private ComboBox<String> fromComboBox;

        @FXML
        private Label result;

        @FXML
        private ComboBox<String> toComboBox;

        //from the inteface (exterior)
        public ExchangeCalculator bizLogic;


        @FXML
        void initialize() {
            //initialize the business logic facade (exterior) used by the controller
            this.bizLogic = new BarcenaysCalculator();

            // initialize toComboBox
            fromComboBox.setItems(FXCollections.observableArrayList(bizLogic.getCurrencyLongNames()));

            // initialize fromComboBox
            toComboBox.setItems(FXCollections.observableArrayList(bizLogic.getCurrencyLongNames()));

            result.setBackground(new Background(
                    new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }

        @FXML
        void onClick(ActionEvent event) {
            try {
                double origAmount = Double.parseDouble(amountTextField.getText());

					/* Invalid numbers will be trapped in the catch block. But
					   non-positive numbers will also be discarded */
                if (origAmount <= 0) throw new NumberFormatException();
                String origCurrency = fromComboBox.getValue();
                origCurrency = origCurrency.substring(0, 3);
                String endCurrency = toComboBox.getValue();
                endCurrency = endCurrency.substring(0, 3);

					/* If both chosen currencies are equal the online converter
					   won't provide a result */
                if (origCurrency.equals(endCurrency)) {
                    result.setText("Please select different currencies");
                
                } else {
                    try {
                        double destAmount = bizLogic.getChangeValue(endCurrency, origAmount, endCurrency);
                   
                        NumberFormat twoDecimal = NumberFormat.getNumberInstance(Locale.US);
                        twoDecimal.setMaximumFractionDigits(2);
                        twoDecimal.setRoundingMode(RoundingMode.FLOOR);
                        if (destAmount < 0) {
                            result.setText("Minimum commission is 3.00€");
                            return;
                        }
                        result.setText(twoDecimal.format(destAmount));

                    } catch (Exception e1) {
                        // e1.printStackTrace();
                        result.setText("Conversion could not be done");
                    }
                }
           
            } catch (NumberFormatException e2) {
                result.setText("Please introduce a valid amount");
            }
        }

}
