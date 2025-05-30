from fastapi import FastAPI, Request
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware
from filter import is_topic_allowed
from gpt_service import ask_gpt
from sentiment import analyze_sentiment

app = FastAPI()

# Zezwalamy na połączenia z React frontend
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000"],
    allow_methods=["*"],
    allow_headers=["*"],
)

class ChatRequest(BaseModel):
    user_input: str

@app.post("/chat/")
async def chat_endpoint(req: ChatRequest):
    if not is_topic_allowed(req.user_input):
        return {
            "response": "Można pytać tylko o tematy związane ze sklepem i ubraniami.",
            "sentiment": "neutral"
        }

    gpt_response = ask_gpt(req.user_input)
    sentiment = analyze_sentiment(gpt_response)

    return {
        "response": gpt_response,
        "sentiment": sentiment
    }
