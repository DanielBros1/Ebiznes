from transformers import AutoTokenizer, AutoModelForCausalLM, pipeline

MODEL_NAME = "flax-community/papuGaPT2"

tokenizer = AutoTokenizer.from_pretrained(MODEL_NAME)
model = AutoModelForCausalLM.from_pretrained(MODEL_NAME)
text_generator = pipeline("text-generation", model=model, tokenizer=tokenizer)

GREETINGS = [
    "Witaj w naszym sklepie! Jak mogę Ci pomóc?",
    "Dzień dobry! W czym mogę pomóc?",
    "Cześć! Jakie ubrania Cię interesują?",
    "Witaj! Potrzebujesz pomocy w wyborze ubrań?",
    "Hej! Jak mogę Ci pomóc w zakupach?"
]

GOODBYES = [
    "Dziękuję za rozmowę! Miłych zakupów!",
    "Do zobaczenia w naszym sklepie!",
    "Mam nadzieję, że pomogłem. Do widzenia!",
    "Życzę udanych zakupów! Do zobaczenia!",
    "Dziękuję i zapraszamy ponownie!"
]
