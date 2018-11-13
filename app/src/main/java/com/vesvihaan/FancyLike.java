package com.vesvihaan;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

public class FancyLike {
    private static ArrayList<String> getLikerNames(ArrayList<User> likers){
        ArrayList<String> likerNames=new ArrayList<>();
        if(likers.size()==0){
            likerNames.add("Be first to like");
        }
        else {
            for (User user : likers) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null && user.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    likerNames.add(0, "you");
                } else {
                    likerNames.add(user.getUserName());
                }
            }
        }
        return likerNames;
    }

    public static String showFancyLikes(ArrayList<User> likers){
        ArrayList<String> likerNames=getLikerNames(likers);
        String string="";
        if(likerNames.size()==1){
            return likerNames.get(0);
        }
        else if(likerNames.size()==2){
            return likerNames.get(0)+" and "+likerNames.get(1);
        }
        else if(likerNames.size()==3){
            return likerNames.get(0)+", "+likerNames.get(1)+" and 1 more";
        }
        else if(likerNames.size()>3){
            int i=0;
            return likerNames.get(0)+", "+likerNames.get(1)+" and "+(likerNames.size()-2)+" others";
        }
        return string;
    }

}
