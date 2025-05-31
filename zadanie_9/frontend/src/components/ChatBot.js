import React, { useState } from "react";

function ChatBot() {
    const [userInput, setUserInput] = useState("");
    const [response, setResponse] = useState("");
    const [sentiment, setSentiment] = useState("");

    const handleSend = async () => {
        if (!userInput) return;

        try {
            const res = await fetch("http://localhost:8000/chat/", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ user_input: userInput }),
            });

            const data = await res.json();
            setResponse(data.response);
            setSentiment(data.sentiment);
        } catch (error) {
            console.error("Błąd połączenia:", error);
            setResponse("Błąd połączenia z serwerem");
        }
    };

    return (
        <div style={{ maxWidth: "600px", margin: "2rem auto", padding: "2rem", border: "1px solid #ccc", borderRadius: "10px" }}>
            <h2>🛍️ Asystent sklepu – ChatBot</h2>
            <input
                type="text"
                value={userInput}
                onChange={(e) => setUserInput(e.target.value)}
                placeholder="Zadaj pytanie o ubrania lub sklep..."
                style={{ width: "100%", padding: "10px", marginBottom: "1rem" }}
            />
            <button onClick={handleSend} style={{ padding: "10px 20px" }}>Wyślij</button>

            {response && (
                <div style={{ marginTop: "2rem" }}>
                    <p><strong>Odpowiedź:</strong> {response}</p>
                    <p><strong>Sentyment:</strong> {sentiment}</p>
                </div>
            )}
        </div>
    );
}

export default ChatBot;
