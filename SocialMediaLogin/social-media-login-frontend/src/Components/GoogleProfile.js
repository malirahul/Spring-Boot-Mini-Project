import React, { useState, useEffect } from "react";
import axios from "axios";
import "../Profile.css";
import { useNavigate } from "react-router-dom";

const GoogleProfile = () => {
  const [user, setUser] = useState(null);
  const [email, setEmail] = useState("");
  const [picture, setPicture] = useState("");
  const [name, setName] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get(
          "https://www.googleapis.com/drive/v2/files?access_token="
        );

        setUser(response.data);
        setEmail(response.data.email);
        setPicture(response.data.picture);
        setName(response.data.name);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUser();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("userData");

    setUser(null);
    setEmail("");
    setPicture("");
    setName("");

    navigate("/login");
  };

  if (!user) {
    return (
      <div style={{ textAlign: "center", marginTop: "20px" }}>
        Loading user profile...
      </div>
    );
  }

  return (
    <div className="container">
      <div className="profile">
        <div className="profile-info">
          <img className="avatar" src={picture} alt="User Avatar" />
          <h2 className="name">{name}</h2>
          <p className="email">Email: {email}</p>
          <button onClick={handleLogout} className="profile-link">
            Logout
          </button>
        </div>
      </div>
    </div>
  );
};

export default GoogleProfile;
