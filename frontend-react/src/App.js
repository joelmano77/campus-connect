import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Routes, Route, Navigate, Link } from 'react-router-dom';
import { logout, setUser } from './store/slices/authSlice';
import Login from './components/Login';
import Signup from './components/Signup';
import Dashboard from './components/Dashboard';
import NotificationFeed from './components/NotificationFeed';
import UploadMaterial from './components/UploadMaterial';
import InterviewExperience from './components/InterviewExperience';
import AIChatbot from './components/AIChatbot';
import axios from 'axios';

function App() {
  const { isAuthenticated, user } = useSelector((state) => state.auth);
  const dispatch = useDispatch();

  useEffect(() => {
    const storedToken = localStorage.getItem('token');
    if (storedToken && !isAuthenticated) {
      // Validate token with backend to get user data
      axios.get('http://localhost:8081/api/auth/me', {
        headers: { Authorization: `Bearer ${storedToken}` }
      })
      .then(response => {
        dispatch(setUser(response.data));
      })
      .catch(() => {
        localStorage.removeItem('token');
      });
    }
  }, [dispatch, isAuthenticated]);

  const handleLogout = () => {
    dispatch(logout());
  };

  const ProtectedRoute = ({ children, allowedRoles }) => {
    if (!isAuthenticated) {
      return <Navigate to="/login" />;
    }
    if (allowedRoles && !allowedRoles.includes(user?.role)) {
      return <Navigate to="/dashboard" />;
    }
    return children;
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {isAuthenticated && (
        <nav className="bg-white shadow-sm">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <div className="flex justify-between h-16">
              <div className="flex">
                <div className="flex-shrink-0 flex items-center">
                  <h1 className="text-xl font-bold text-gray-900">CampusConnect</h1>
                </div>
                <div className="hidden sm:ml-6 sm:flex sm:space-x-8">
                  <Link to="/dashboard" className="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                    Dashboard
                  </Link>
                  <Link to="/notifications" className="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                    Notifications
                  </Link>
                  {user?.role === 'FACULTY' && (
                    <Link to="/upload-material" className="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                      Upload Material
                    </Link>
                  )}
                  <Link to="/interview-experiences" className="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                    Interview Experiences
                  </Link>
                  {user?.role === 'STUDENT' && (
                    <Link to="/ai-chatbot" className="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                      AI Chatbot
                    </Link>
                  )}
                </div>
              </div>
              <div className="hidden sm:ml-6 sm:flex sm:items-center">
                <button
                  onClick={handleLogout}
                  className="bg-white p-1 rounded-full text-gray-400 hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                >
                  Logout
                </button>
              </div>
            </div>
          </div>
        </nav>
      )}

      <Routes>
        <Route path="/login" element={isAuthenticated ? <Navigate to="/dashboard" /> : <Login />} />
        <Route path="/signup" element={isAuthenticated ? <Navigate to="/dashboard" /> : <Signup />} />
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />
        <Route
          path="/notifications"
          element={
            <ProtectedRoute>
              <NotificationFeed />
            </ProtectedRoute>
          }
        />
        <Route
          path="/upload-material"
          element={
            <ProtectedRoute allowedRoles={['FACULTY']}>
              <UploadMaterial />
            </ProtectedRoute>
          }
        />
        <Route
          path="/interview-experiences"
          element={
            <ProtectedRoute>
              <InterviewExperience />
            </ProtectedRoute>
          }
        />
        <Route
          path="/ai-chatbot"
          element={
            <ProtectedRoute allowedRoles={['STUDENT']}>
              <AIChatbot />
            </ProtectedRoute>
          }
        />
        <Route path="/" element={<Navigate to={isAuthenticated ? "/dashboard" : "/login"} />} />
      </Routes>
    </div>
  );
}

export default App;
