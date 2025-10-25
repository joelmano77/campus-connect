import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';

const InterviewExperience = () => {
  const { user, token } = useSelector((state) => state.auth);
  const [experiences, setExperiences] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    company: '',
    role: '',
    experience: '',
    mistakes: '',
    preparationTips: '',
    isPublic: true,
  });

  // ✅ Corrected backend API base path to match controller: /api/interview-experiences
  useEffect(() => {
    const fetchExperiences = async () => {
      try {
        const res = await axios.get('http://localhost:8080/api/interview-experiences', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setExperiences(res.data);
      } catch (error) {
        console.error('Failed to fetch experiences', error);
      }
    };
    if (token) fetchExperiences();
  }, [token]);

  // ✅ Submit handler
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/interview-experiences', formData, {
        headers: { Authorization: `Bearer ${token}` },
      });

      // Refresh experiences after submit
      const res = await axios.get('http://localhost:8080/api/interview-experiences', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setExperiences(res.data);

      // Reset form
      setShowForm(false);
      setFormData({
        company: '',
        role: '',
        experience: '',
        mistakes: '',
        preparationTips: '',
        isPublic: true,
      });
    } catch (error) {
      console.error('Failed to submit experience', error);
    }
  };

  // ✅ Summarize button handler
  const handleSummarize = async (experienceId) => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/interview-experiences/${experienceId}/summarize`,
        { headers: { Authorization: `Bearer ${token}` } }
      );
      alert(`AI Summary: ${res.data.summary || 'No summary available'}`);
    } catch (error) {
      console.error('Failed to summarize', error);
    }
  };

  return (
    <div className="max-w-4xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-900">Interview Experiences</h1>
        {user?.role === 'STUDENT' && (
          <button
            onClick={() => setShowForm(!showForm)}
            className="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-2 px-4 rounded"
          >
            Share Experience
          </button>
        )}
      </div>

      {/* ✅ Form Section */}
      {showForm && (
        <form onSubmit={handleSubmit} className="bg-white p-6 rounded-lg shadow-md mb-6">
          <div className="grid grid-cols-1 gap-6">
            <div>
              <label className="block text-sm font-medium text-gray-700">Company</label>
              <input
                type="text"
                value={formData.company}
                onChange={(e) => setFormData({ ...formData, company: e.target.value })}
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Role</label>
              <input
                type="text"
                value={formData.role}
                onChange={(e) => setFormData({ ...formData, role: e.target.value })}
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Experience</label>
              <textarea
                value={formData.experience}
                onChange={(e) => setFormData({ ...formData, experience: e.target.value })}
                rows={4}
                required
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Mistakes</label>
              <textarea
                value={formData.mistakes}
                onChange={(e) => setFormData({ ...formData, mistakes: e.target.value })}
                rows={3}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700">Preparation Tips</label>
              <textarea
                value={formData.preparationTips}
                onChange={(e) => setFormData({ ...formData, preparationTips: e.target.value })}
                rows={3}
                className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div className="flex items-center">
              <input
                id="isPublic"
                type="checkbox"
                checked={formData.isPublic}
                onChange={(e) => setFormData({ ...formData, isPublic: e.target.checked })}
                className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
              />
              <label htmlFor="isPublic" className="ml-2 block text-sm text-gray-900">
                Make public
              </label>
            </div>
          </div>
          <div className="mt-6">
            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              Submit
            </button>
          </div>
        </form>
      )}

      {/* ✅ Display Experiences */}
      <div className="space-y-6">
        {experiences.map((exp) => (
          <div key={exp.id} className="bg-white shadow overflow-hidden sm:rounded-md">
            <div className="px-4 py-5 sm:p-6">
              <h3 className="text-lg leading-6 font-medium text-gray-900">
                {exp.company} - {exp.role}
              </h3>
              <p className="mt-2 text-sm text-gray-500">{exp.experience}</p>
              {exp.mistakes && (
                <p className="mt-2 text-sm text-red-600">Mistakes: {exp.mistakes}</p>
              )}
              {exp.preparationTips && (
                <p className="mt-2 text-sm text-green-600">Tips: {exp.preparationTips}</p>
              )}
              <div className="mt-4 flex justify-between items-center">
                <p className="text-sm text-gray-400">Shared by: {exp.sharedBy || 'Anonymous'}</p>
                <button
                  onClick={() => handleSummarize(exp.id)}
                  className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 rounded text-xs"
                >
                  Summarize with AI
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default InterviewExperience;
