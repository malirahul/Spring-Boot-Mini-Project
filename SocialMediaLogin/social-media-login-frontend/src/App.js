import "@shopify/polaris/build/esm/styles.css";
import "./App.css";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import LogIn from "./Components/LogIn";
import GitHubProfile from "./Components/GitHubProfile";
import GoogleProfile from "./Components/GoogleProfile";

const App = () => {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/login" exact element={<LogIn />}></Route>
          <Route
            path="/githubProfile"
            exact
            element={<GitHubProfile />}
          ></Route>
          <Route
            path="/googleProfile"
            exact
            element={<GoogleProfile />}
          ></Route>
        </Routes>
      </Router>
    </div>
  );
};

export default App;
