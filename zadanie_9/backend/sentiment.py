from textblob import TextBlob

def analyze_sentiment(text: str) -> str:
    blob = TextBlob(text)
    polarity = blob.sentiment.polarity
    if polarity > 0.2:
        return "pozytywny"
    elif polarity < -0.2:
        return "negatywny"
    else:
        return "neutralny"
