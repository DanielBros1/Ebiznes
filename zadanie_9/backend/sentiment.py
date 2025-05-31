import nltk
from nltk.sentiment import SentimentIntensityAnalyzer

nltk.download('vader_lexicon', quiet=True)
nltk.download('punkt', quiet=True)

def analyze_sentiment(text: str) -> str:
    sia = SentimentIntensityAnalyzer()
    score = sia.polarity_scores(text)['compound']

    if score >= 0.05:
        return "pozytywny"
    elif score <= -0.05:
        return "negatywny"
    else:
        return "neutralny"
