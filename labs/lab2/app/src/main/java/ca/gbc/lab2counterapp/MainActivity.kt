package ca.gbc.lab2counterapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // 2. Output initial value is zero
    private var counter = 0
    // 3 & 6. Step behavior: 1 by default, changes to 2 on "Step" click
    private var stepValue = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // View references from your activity_main.xml IDs
        val textOutput = findViewById<TextView>(R.id.textOutput)
        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
        val buttonPlus = findViewById<Button>(R.id.buttonPlus)
        val buttonReset = findViewById<Button>(R.id.buttonReset)
        val buttonStep = findViewById<Button>(R.id.buttonStep)

        // 3. Add button: increases output by stepValue (default 1)
        buttonPlus.setOnClickListener {
            counter += stepValue
            textOutput.text = counter.toString()
        }

        // 4. Subtract button: decreases output by stepValue (default 1)
        buttonMinus.setOnClickListener {
            counter -= stepValue
            textOutput.text = counter.toString()
        }

        // 5. Reset button: resets output to zero and returns to default behavior (step = 1)
        buttonReset.setOnClickListener {
            counter = 0
            stepValue = 1
            textOutput.text = counter.toString()
        }

        // 6. Step button: changes behavior to increase/decrease by two
        buttonStep.setOnClickListener {
            stepValue = 2
        }
    }
}