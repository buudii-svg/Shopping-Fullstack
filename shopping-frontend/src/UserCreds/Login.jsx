import { Layout } from "antd";
import { useState } from "react";
import { useHistory, NavLink } from "react-router-dom";
import axios from "axios";
const { Content } = Layout;
function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();
  // onchange methods
  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };
  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await axios.post(
      "http://localhost:8080/shopping-1.0-SNAPSHOT/api/user/login",
      {
        username: username,
        password: password,
      }
    );
    if (res.data === "Unauthorized") {
      alert("Invalid username or password!");
    } else {
      localStorage.setItem("token", res.data);
      history.replace("/");
      window.location.reload();
    }
    console.log(res.data);
  };

  return (
    <Content
      style={{
        padding: "50px 50px",
        width: "50%",
        margin: "auto",
      }}
    >
      <div className="card" style={{ color: "#fff" }}>
        <div className="card-header bg-dark text-center">
          <h5>Login</h5>
        </div>
        <div className="card-body text-center text-dark bg-light">
          <form className="row">
            <div className="mb-3 row">
              <label for="Username" className="col-sm-2 col-form-label">
                Username
              </label>
              <div className="col-sm-10">
                <input
                  type="text"
                  onChange={handleUsernameChange}
                  className="form-control"
                  id="Username"
                />
              </div>
            </div>
            <div className="mb-3 row">
              <label for="inputPassword" className="col-sm-2 col-form-label">
                Password
              </label>
              <div className="col-sm-10">
                <input
                  type="password"
                  onChange={handlePasswordChange}
                  className="form-control"
                  id="inputPassword"
                />
              </div>
            </div>
            <div className="mb-3 row">
              <NavLink style={{ textDecoration: "none" }} to="/register">
                Don't have an account ?
              </NavLink>
            </div>
            <div>
              <button
                onClick={handleSubmit}
                type="submit"
                className="btn btn-primary mb-3"
              >
                Login
              </button>
            </div>
          </form>
        </div>
      </div>
    </Content>
  );
}
export default Login;
