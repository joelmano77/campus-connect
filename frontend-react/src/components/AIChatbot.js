import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';

const AIChatbot = () => {
  const { token } = useSelector((state) => state.auth);
  const [query, setQuery] = useState('');
  const [response, setResponse] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!query.trim()) {
      setResponse('Please enter a question.');
      return;
    }
    setResponse(''); // Clear previous response
    try {
      const res = await axios.post('http://localhost:8081/api/chat/chat', { query }, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setResponse(res.data.answer);
    } catch (error) {
      console.error('Chat failed', error);
      setResponse('Error: Unable to get response from AI. Please try again.');
    }
  };

  return (
    <div className="max-w-2xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-6">AI Chatbot</h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="query" className="block text-sm font-medium text-gray-700">Ask a placement-related question</label>
          <textarea
            id="query"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            rows={4}
            placeholder="e.g., How to prepare for Infosys interview?"
            className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
          />
        </div>
        <button
          type="submit"
          className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
        >
          Ask AI
        </button>
      </form>
      {response && (
        <div className="mt-6 bg-white shadow overflow-hidden sm:rounded-md">
          <div className="px-4 py-5 sm:p-6">
            <h3 className="text-lg leading-6 font-medium text-gray-900">AI Response</h3>
            <p className="mt-2 text-sm text-gray-500">{response}</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default AIChatbot;
