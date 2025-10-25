from flask import Flask, request, jsonify
import joblib
import numpy as np
from sklearn.linear_model import LogisticRegression
import os

app = Flask(__name__)

# Load or train a simple model (dummy data for demo)
model_path = 'performance_model.pkl'
if os.path.exists(model_path):
    model = joblib.load(model_path)
else:
    # Dummy training data: attendance, marks, risk (0=low, 1=high)
    X = np.array([[85, 90], [70, 75], [95, 95], [60, 65], [80, 85]])
    y = np.array([0, 1, 0, 1, 0])
    model = LogisticRegression()
    model.fit(X, y)
    joblib.dump(model, model_path)

@app.route("/")
def home():
    return "ML Service is running!"

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json()
    attendance = data.get("attendance", 0)
    marks = data.get("marks", 0)

    if attendance < 0 or attendance > 100 or marks < 0 or marks > 100:
        return jsonify({"error": "Invalid input values"}), 400

    prediction = model.predict([[attendance, marks]])[0]
    probability = model.predict_proba([[attendance, marks]])[0][1]

    risk = "High Risk" if prediction == 1 else "Low Risk"
    return jsonify({
        "risk": risk,
        "probability": round(probability * 100, 2)
    })

if __name__ == "__main__":
    app.run(debug=True, port=5001)
