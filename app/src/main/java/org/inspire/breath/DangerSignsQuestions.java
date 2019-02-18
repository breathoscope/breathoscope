package org.inspire.breath;

public class DangerSignsQuestions {
    public String qList[] = {
            "Does the patient have convulsions?",
            "Is the patient lethargic or unconscious?",
            "Is the patient vomiting everything?",
            "Is the patient unable to breastfeed or drink?"
    };

    public String aList[] = {
            "Patient could have cerebral malaria or meningitis. Immediate medical treatment required. Do rapid diagnostic tests and if their result is positive, give the patient pre-referral malaria treatment." ,
            "The patient may be dehydrated, have hypoglycaemia, cerebral malaria or meningitis. Perform rapid diagnostic tests and if their result is positive, give the patient pre-referral malaria treatment." +
                    " If patient has diarrhea and shows signs of dehydration treat with oral rehydration salts solution during referral, but only if the patient is able to drink and swallow.",
            "The patient needs referral to a health center or hospital, as child might vomit all medication, oral rehydration salts solution. Can be a sign of meningitis.",
            "This can be a sign of severe respiratory distress, general severe illness. Therapy will fail"
    };

    public String anyDangersSigns() {
        return "The patient needs urgent referral to a health center or hospital. While awaiting referral, full assessment should be performed on the patient," +
            " including if possible a malaria rapid diagnostic test, and be given rectal artesunate as a pre-referral treatment in case of severe malaria. Don't delay the referral whilst waiting for a test result." +
            " In that case, give rectal artesunate presumptively.";
    }

    public String retQ(int i){
        return this.qList[i];
    }

    public String retA(int i){
        return this.qList[i];
    }
}
