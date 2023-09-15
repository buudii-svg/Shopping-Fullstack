import { Layout } from "antd";
import axios from "axios";
import { useEffect, useState } from "react";
import { useHistory, NavLink } from "react-router-dom";
const { Content } = Layout;
function RepresentativeRegister() {
  const [username, setUsername] = useState("");
  const [companyId, setCompanyId] = useState("");
  const [companies, setCompanies] = useState([]);
  const history = useHistory();
  // onchange methods
  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };
  const handleCompanyChange = (e) => {
    setCompanyId(e.target.value);
    console.log(companyId);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const res = await axios.post(
      "http://localhost:8080/shopping-1.0-SNAPSHOT/api/comp",
      {
        username: username,
        company: {
          id: companyId,
        },
      }
    );
    console.log(res.data);
    if (res.data === "Representative already exists!") {
      alert("Username already exists!");
    } else if (res.data === "Representative added successfully!") {
      alert("Registration done successfully!");
      //   history.push("/login");
    }
  };
  useEffect(() => {
    const getCompanies = async () => {
      const res = await axios.get(
        "http://localhost:8080/shopping-1.0-SNAPSHOT/api/selling/company"
      );
      setCompanies(res.data);
    };
    getCompanies();
  }, []);

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
          <h5>Create Seller Account</h5>
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
                  placeholder="Enter username"
                  onChange={handleUsernameChange}
                />
              </div>
            </div>
            <div className="mb-3 row">
              <label for="Region" className="col-sm-2 col-form-label">
                Company
              </label>
              <div className="col-sm-10">
                <select
                  onChange={handleCompanyChange}
                  class="form-select"
                  aria-label="Default select example"
                >
                  <option selected disabled>
                    Open this select menu
                  </option>
                  {companies.map((company) => {
                    return (
                      <>
                        <option value={company.id}>{company.name}</option>
                      </>
                    );
                  })}
                </select>
              </div>
            </div>
            <div>
              <button
                onClick={handleSubmit}
                type="submit"
                className="btn btn-primary mb-3"
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
export default RepresentativeRegister;
