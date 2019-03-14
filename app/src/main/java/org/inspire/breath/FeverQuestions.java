package org.inspire.breath;

public class FeverQuestions {
    /*A list of questions*/
    private String qList[] = {
            "Does the child have danger signs?",
            "Is the child older than 2 months and younger than 5 years with fever or history of fever in the past 48 hours?",
            "Can you perform a malaria pan pLDH rapid diagnostic test and give the result?",
            "Is the child showing signs of severe malaria such as: being unconscious/prostrate, convulsions, lethargy, severe pallor, unable to drink/vomiting, respiratory distress or cyanosis?",
            "Has the child been diagnosed and treated fully for malaria in the past 28 days i.e. did the child take ALL doses of ACT for 3 days without vomiting?"
    };

    /*A list of answers*/
    private String aList[] = {
            "Only do mRDT if there is time. Apply pre-referral artesunate. Refer the child to HC/Hospital URGENTLY.",
            "If the child is younger than 2 months or older than 5 years, refer to HC.",
            "Refer the child to health centre",
            "The child has severe malaria: give pre-referral artesunate and refer the child to HC/Hospital URGENTLY.",
            "Option 1: Refer to HC for slide/review: risk of treatment failure especially is soon after first treatment." +
                    "Option 2: If CHW has access to 2nd line treatment consider 2nd line ACTs(careful documentation)." +
                    "Option 3: If no access to blood film or 2nd line treatment: retreat with 1st line ACTs with warning to return of worsens.",
            "Simple Malaria: Treat with oral ACT as per protocol (directly observe 1st dose) plus one dose paracetamol.",
            "Press Next"
    };


    private String ansToQ1[] = {
            "Yes",
            "No"
    };

    private String ansToQ2[] = {
            "No",
            "Yes"
    };

    private String ansToQ3[] = {
            "Negative",
            "Positive"
    };

    private String ansToQ4[] = {
            "Yes",
            "No"
    };

    private String ansToQ5[] = {
            "No", //OR diagnosis/treatment/adherence not known"
            "Yes"
    };

    /*Returns a specific question*/
    public String retQ(int i){
        return this.qList[i];
    }

    /*Returns a specific answer*/
    public String retA(int i){
        return this.aList[i];
    }

    public String retAQ1(int i){
        return this.ansToQ1[i];
    }

    public String retAQ2(int i){
        return this.ansToQ2[i];
    }

    public String retAQ3(int i){
        return this.ansToQ3[i];
    }

    public String retAQ4(int i){
        return this.ansToQ4[i];
    }

    public String retAQ5(int i){
        return this.ansToQ5[i];
    }
}
