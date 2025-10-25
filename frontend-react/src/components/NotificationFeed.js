import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';

const NotificationFeed = () => {
  const { token } = useSelector((state) => state.auth);
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const response = await axios.get('http://localhost:8080/notifications', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setNotifications(response.data);
      } catch (error) {
        console.error('Failed to fetch notifications', error);
      }
    };
    if (token) fetchNotifications();
  }, [token]);

  return (
    <div className="max-w-4xl mx-auto py-6 px-4 sm:px-6 lg:px-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-6">Notifications</h1>
      <div className="space-y-4">
        {notifications.map((notification) => (
          <div key={notification.id} className="bg-white shadow overflow-hidden sm:rounded-md">
            <div className="px-4 py-5 sm:p-6">
              <h3 className="text-lg leading-6 font-medium text-gray-900">{notification.title}</h3>
              <p className="mt-2 max-w-2xl text-sm text-gray-500">{notification.content}</p>
              <p className="mt-3 text-sm text-gray-400">Posted by: {notification.postedBy}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default NotificationFeed;
