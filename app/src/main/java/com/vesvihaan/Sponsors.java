package com.vesvihaan;

import java.util.ArrayList;
import java.util.Map;

public class Sponsors {
    String sponsorCat;
    ArrayList<Sponsor> sponsor;

    public Sponsors(){}

    public String getSponsorCat() {
        return sponsorCat;
    }

    public ArrayList<Sponsor> getSponsor() {
        return sponsor;
    }

    public void setSponsor(ArrayList<Sponsor> sponsor) {
        this.sponsor = sponsor;
    }

    public void setSponsorCat(String sponsorCat) {

        this.sponsorCat = sponsorCat;
    }

    public static class Sponsor {
        String sponsorLogoUrl;
        String sponsorName;
        String sponsorContact;
        public Sponsor(){}
        public String getSponsorLogoUrl() {
            return sponsorLogoUrl;
        }

        public void setSponsorLogoUrl(String sponsorLogoUrl) {
            this.sponsorLogoUrl = sponsorLogoUrl;
        }

        public String getSponsorName() {
            return sponsorName;
        }

        public void setSponsorName(String sponsorName) {
            this.sponsorName = sponsorName;
        }

        public String getSponsorContact() {
            return sponsorContact;
        }

        public void setSponsorContact(String sponsorContact) {
            this.sponsorContact = sponsorContact;
        }
    }
}
