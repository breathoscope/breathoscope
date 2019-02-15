package org.inspire.breath;

public class Questions {

    public String mQuestions [] = {
            "Do you have more then 3 loose stools /24h?",
            "3 weeks without blood or rice water aspect?",
            "Signs of severe dehydration: Sunken eyes, Skin pinch >3 seconds or other danger signs?"
    };

    public String mAnswers [] = {
            "No need for treatment",
            "Refer to HC",
            "Uncomplicated diarrhoea: give ORS and Zinc sulfate according to age, plus Albendazole (if not received within last 6 months)"
    };

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    }

    public String getAnswer(int a){
        String answer = mAnswers[a];
        return answer;
    }
}
