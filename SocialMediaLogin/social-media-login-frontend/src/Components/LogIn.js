import React, { useState } from "react";
import {
  Container,
  Row,
  Col,
  Form,
  FormGroup,
  Label,
  Input,
  Button,
} from "reactstrap";
import { useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";

export default function LogIn() {
  const navigate = useNavigate();

  const [error, setError] = useState(false);
  const [user, setUser] = useState({ username: "", password: "" });

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/registration", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
      });

      if (response.ok) {
        // Handle successful login
        console.log("Login successful");
      } else {
        // Handle login error
        setError(true);
      }
    } catch (error) {
      // Handle network or server error
      console.error("Error:", error);
    }
  };

  const handleChange = (event) => {
    setUser({ ...user, [event.target.name]: event.target.value });
  };

  const handleLogInWithGoogle = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
  };

  const handleLogInWithGitHub = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/github";
  };

  const handleLogIn = () => {
    navigate("/GithubProfile");
  };

  return (
    <div className="Container">
      <nav className="navbar navbar-inverse navbar-fixed-top">
        <h1>Welcome</h1>
      </nav>
      <br />
      <Row>
        <Col md={{ size: 6, offset: 3 }}>
          {error && (
            <div className="alert alert-info">
              Invalid email or password. Please double-check your credentials
              and try again.
            </div>
          )}

          <div>
            <h2>
              <Button color="primary" onClick={handleLogInWithGoogle}>
                Login with Google
              </Button>
            </h2>
          </div>
          <div>
            <h2>
              <Button color="primary" onClick={handleLogInWithGitHub}>
                Login with GitHub
              </Button>
            </h2>
          </div>
          <Form onSubmit={handleSubmit}>
            <FormGroup>
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                name="username"
                value={user.username}
                onChange={handleChange}
                required
                autoFocus
              />
            </FormGroup>
            <FormGroup>
              <Label htmlFor="password">Password</Label>
              <Input
                id="password"
                type="password"
                name="password"
                value={user.password}
                onChange={handleChange}
                required
              />
            </FormGroup>
            <FormGroup>
              <Button type="submit" color="primary" onClick={handleLogIn}>
                Log In
              </Button>
              <span>
                New User? <a href="/registration">Register here</a>
              </span>
            </FormGroup>
          </Form>
        </Col>
      </Row>
    </div>
  );
}
