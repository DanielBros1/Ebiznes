import os
import openai
from dotenv import load_dotenv

load_dotenv("openai.env")
openai.api_key = os.getenv("OPENAI_API_KEY")

def ask_gpt(prompt: str) -> str:
    try:
        response = openai.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[{"role": "user", "content": prompt}]
        )
        return response.choices[0].message.content.strip()
    except Exception as e:
        print("Błąd OpenAI:", e)
        return "Wystąpił błąd podczas komunikacji z ChatGPT."
