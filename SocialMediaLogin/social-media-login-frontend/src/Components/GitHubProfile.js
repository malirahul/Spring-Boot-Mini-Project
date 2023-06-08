import React, { useState, useEffect } from "react";
import axios from "axios";
import "../Profile.css";
import { useNavigate } from "react-router-dom";

const GitHubProfile = () => {
  const [user, setUser] = useState(null);
  const [repositories, setRepositories] = useState([]);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get(
          "https://api.github.com/users/malirahul"
        ); // Replace {username} with the GitHub username
        setUser(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    const fetchRepositories = async () => {
      try {
        const response = await axios.get(
          "https://api.github.com/users/malirahul/repos?sort=updated"
        ); // Replace {username} with the GitHub username
        setRepositories(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUser();
    fetchRepositories();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("userData");

    // Reset state to initial values
    setUser(null);
    setRepositories([]);

    // Redirect the user to the login page or homepage
    navigate("/login");
  };

  if (!user || repositories.length === 0) {
    return (
      <div style={{ textAlign: "center", marginTop: "20px" }}>
        Loading user and repositories...
      </div>
    );
  }

  return (
    <div className="container">
      <div className="profile">
        <div className="profile-info">
          <img className="avatar" src={user.avatar_url} alt="User Avatar" />
          <h2 className="name">{user.name}</h2>
          <p className="username">Username: {user.login}</p>
          <p className="location">Location: {user.location}</p>
          <p className="repos">Public Repos: {user.public_repos}</p>
          <p className="followers">Followers: {user.followers}</p>
          <a
            className="profile-link"
            href={user.html_url}
            target="_blank"
            rel="noopener noreferrer"
          >
            View Profile
          </a>
          <br />
          <button onClick={handleLogout} className="profile-link">
            Logout
          </button>{" "}
          {/* Logout button */}
        </div>
      </div>

      <div className="repository-list">
        <h1>Github User Details</h1>
        <h2>Repositories</h2>
        {repositories.map((repo) => (
          <div key={repo.id} className="repository">
            <h4 className="repo-name">{repo.name}</h4>
            <p className="repo-description">{repo.description}</p>
            <div className="repo-details">
              <p className="repo-language">
                <strong>Language:</strong> {repo.language}
              </p>
              <p className="repo-stars">
                <strong>Stars:</strong> {repo.stargazers_count}
              </p>
              <p className="repo-watchers">
                <strong>Watchers:</strong> {repo.watchers_count}
              </p>
              <p className="repo-forks">
                <strong>Forks:</strong> {repo.forks_count}
              </p>
            </div>
            <a
              className="repo-link"
              href={repo.html_url}
              target="_blank"
              rel="noopener noreferrer"
            >
              View Repository
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};

export default GitHubProfile;
