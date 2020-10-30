package com.example.sportsnews.utilities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MostFreqUsedMethods
{
    public static void goToFragment(View view, int btnID, final int action)
    {
        final NavController navController = Navigation.findNavController(view);
        CardView bt=view.findViewById(btnID);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(action);
            }
        });

    }


}
