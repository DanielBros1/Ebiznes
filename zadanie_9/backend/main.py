from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware

from models import UserInput, ChatResponse
from sentiment import analyze_sentiment
from chat_logic import generate_chat_response

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.post("/chat/", response_model=ChatResponse)
async def chat(user_input: UserInput):
    if not user_input.user_input.strip():
        raise HTTPException(status_code=400, detail="Pole wejściowe nie może być puste")

    response_text = generate_chat_response(user_input.user_input)
    sentiment = analyze_sentiment(user_input.user_input)

    return {
        "response": response_text,
        "sentiment": sentiment
    }

