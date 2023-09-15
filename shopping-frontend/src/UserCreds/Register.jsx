import { Layout } from "antd";
import axios from "axios";
import { useState } from "react";
import { useHistory, NavLink } from "react-router-dom";
const { Content } = Layout;
function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [region, setRegion] = useState("");
  const history = useHistory();
  // onchange methods
  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };
  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };
  const handleRegionChange = (e) => {
    setRegion(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await axios.post(
      "http://localhost:8080/shopping-1.0-SNAPSHOT/api/user",
      {
        username: username,
        password: password,
        region: region,
      }
    );
    if (res.data === "User already exists!") {
      alert("Username already exists!");
    } else if (res.data === "User added successfully!") {
      alert("Registration done successfully!");
      history.push("/login");
    }
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
          <h5>Register</h5>
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
                  required
                  className="form-control"
                  id="Username"
                  onChange={handleUsernameChange}
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
                  className="form-control"
                  required
                  id="inputPassword"
                  onChange={handlePasswordChange}
                />
              </div>
            </div>
            <div className="mb-3 row">
              <label for="Region" className="col-sm-2 col-form-label">
                Region
              </label>
              <div className="col-sm-10">
                <input
                  required
                  onChange={handleRegionChange}
                  type="text"
                  className="form-control"
                  id="Region"
                />
              </div>
            </div>
            <div>
              <button
                onClick={handleSubmit}
                type="submit"
                className="btn btn-success mb-3"
              >
                Register
              </button>
            </div>
          </form>
        </div>
      </div>
    </Content>
  );
}
export default Register;
