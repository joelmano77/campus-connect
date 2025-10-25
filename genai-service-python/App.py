from flask import Flask, request, jsonify
import google.generativeai as genai
import os

app = Flask(__name__)

# 🔑 Set your Google Generative AI API key (make sure it's set as an environment variable)
genai.configure(api_key="AIzaSyB--0k-Ea4K7eCKzYNc74oX2SoJXGG_46Q")

@app.route("/")
def home():
    return "GenAI Service is running!"

@app.route("/generate", methods=["POST"])
def generate_text():
    data = request.get_json()
    prompt = data.get("prompt", "")

    if not prompt:
        return jsonify({"error": "Prompt is required"}), 400

    try:
        # ⚙️ Call Google Generative AI API
        model = genai.GenerativeModel("gemini-2.0-flash-exp")
        response = model.generate_content(prompt)
        text_output = response.text
        return jsonify({"response": text_output})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route("/summarize", methods=["POST"])
def summarize():
    data = request.get_json()
    text = data.get("text", "")

    if not text:
        return jsonify({"error": "Text is required"}), 400

    prompt = f"Summarize the following interview experience in 2-3 sentences, highlighting key points, mistakes, and preparation tips:\n\n{text}"

    try:
        model = genai.GenerativeModel("gemini-2.0-flash-exp")
        response = model.generate_content(prompt)
        summary = response.text
        return jsonify({"summary": summary})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

@app.route("/chat", methods=["POST"])
def chat():
    data = request.get_json()
    query = data.get("query", "")

    if not query:
        return jsonify({"error": "Query is required"}), 400

    prompt = f"Answer this placement-related question as a helpful AI assistant: {query}"

    try:
        model = genai.GenerativeModel("gemini-2.0-flash-exp")
        response = model.generate_content(prompt)
        answer = response.text
        return jsonify({"answer": answer})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(debug=True, port=5005)
