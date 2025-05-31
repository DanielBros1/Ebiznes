from config import GREETINGS, GOODBYES, text_generator

def is_about_clothing_or_shop(text: str) -> bool:
    clothing_keywords = {
        'ubrania', 'ubranie', 'odzież', 'spodnie', 'koszula',
        'sukienka', 'bluza', 'sweter', 'buty', 'sklep', 'zakupy',
        'kupić', 'zamówić', 'rozmiar', 'kolor', 'marka', 'cena'
    }
    tokens = set(word.lower() for word in text.split())
    return bool(tokens & clothing_keywords)

def generate_chat_response(user_input: str) -> str:
    input_lower = user_input.lower()

    if any(greet in input_lower for greet in ["witaj", "cześć", "dzień dobry", "dobry wieczór"]):
        return GREETINGS[len(user_input) % len(GREETINGS)]

    if any(goodbye in input_lower for goodbye in ["do widzenia", "dziękuję", "dzięki", "miłego dnia"]):
        return GOODBYES[len(user_input) % len(GOODBYES)]

    if not is_about_clothing_or_shop(user_input):
        return "Przepraszam, ale mogę pomóc tylko w pytaniach dotyczących ubrań i naszego sklepu."

    try:
        prompt = (
            f"Jesteś pomocnym asystentem sklepu odzieżowego. "
            f"Odpowiadasz tylko na pytania dotyczące ubrań, mody i zakupów. "
            f"Pytanie: {user_input}\nOdpowiedź:"
        )

        generated = text_generator(
            prompt,
            max_length=100,
            num_return_sequences=1,
            temperature=0.7,
            repetition_penalty=1.2,
            do_sample=True
        )

        full_text = generated[0]['generated_text']
        response = full_text.replace(prompt, "").strip()
        return response.split(".")[0] + "."
    except Exception as e:
        return "Wystąpił błąd podczas generowania odpowiedzi."
