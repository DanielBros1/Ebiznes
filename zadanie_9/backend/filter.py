ALLOWED_KEYWORDS = ["ubrania", "odzież", "sklep", "buty", "koszulka", "spodnie", "zakupy", "moda", "dostawa", "zwrot"]

def is_topic_allowed(text: str) -> bool:
    text_lower = text.lower()
    return any(keyword in text_lower for keyword in ALLOWED_KEYWORDS)
