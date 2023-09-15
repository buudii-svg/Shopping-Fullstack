import { Layout } from "antd";
import { useHistory } from "react-router-dom";

const { Content } = Layout;
function SharedLogin() {
  const history = useHistory();

  const sellerLogin = () => {
    history.push("/seller/login");
  };
  const clientLogin = () => {
    history.push("/client/login");
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
              <div className="col-sm-6">
                <button
                  onClick={clientLogin}
                  type="submit"
                  className="btn btn-primary mb-3"
                >
                  Login as a <b>client</b>
                </button>
              </div>
              <div className="col-sm-6">
                <button
                  onClick={sellerLogin}
                  type="submit"
                  className="btn btn-primary mb-3"
                >
                  Login as a <b>seller</b>
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </Content>
  );
}
export default SharedLogin;
