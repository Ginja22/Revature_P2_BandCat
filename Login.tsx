import axios from "axios";
import { useState } from "react";
import { Link, Navigate } from "react-router-dom";
import User from "./model/User";
import background from "./KUBU.png";

// Props to update the current user
interface Props {
  currentUser: User | null;
  setCurrentUser: any
}

// Function to handle the login process
export default function Login({ currentUser, setCurrentUser }: Props) {
  // Login success tracking
  const [loginSuccess, setLoginSuccess] = useState(false);

  // Fields that user give input for
  const [inputFields, setInputFields] = useState(
    {
      username: "",
      password: ""
    }
  );

  // Gets User input for each field based on its name
  function getInput(event: any) {
    setInputFields({
      ...inputFields,
      [event.target.name]: event.target.value
    });
  }

  // Function for when Login button is pressed
  async function login() {
    // The info to check in API
    let checkUser: User = {
      username: inputFields.username,
      password: inputFields.password
    }

    // Check info in API
    let returnedUser = await axios.post('http://localhost:8080/login',
      JSON.stringify(checkUser),
      {
        'headers':
        {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      })
      .then((response) => response.data);

    // Check Login Success, if ID is 0 => login failed, otherwise, set up necessary login variables
    returnedUser.userID == 0 ?
      (console.log("LOGIN FAILED")) :
      (setUp(returnedUser));
  }

  // Set log in variables
  function setUp(returnedUser: User) {
    setCurrentUser(returnedUser); // Update current user
    setLoginSuccess(true);        // Set log in success to true
  }


  // What to show => if login was a success, navigate to search page, otherwise, show login
  return loginSuccess ? (<Navigate to="/search" />) :
    (
      <>
        <div className="backgroundColor">
          <img className="bandCat" src={require('../KUBU.png')}></img>
          <div className="center">
            
            <br></br>
            <br></br>
          <div className="topBox">
            
            <div className="homeText">BandCat Instruments - Login</div>
          </div>
          <br></br>
          <br></br>

            {/* style={{ backgroundImage: `url(${'./KUBU.png'})`,
                  width: '100px' }} IMPORTANT TAKE A LOOK AT LATER!!!*/}



            {/* <header className="App-header">
        <nav><img src="KUBU.png" className="bandCat"></img></nav>

        <div className="topBox">

          <nav className="homeText">
            <h1 className="center">BandCat Instruments - Home</h1>
          </nav>
          <nav><img src="KUBU.png" className="bandCat"></img></nav>

        </div>
      </header> */}

            {/* Login Form */}
            <form>

              {/* Username Field */}
              <input type="text" placeholder="username" name="username" value={inputFields.username} onChange={getInput} />
              <br />

              {/* Password Field */}
              <input type="password" placeholder="password" name="password" value={inputFields.password} onChange={getInput} />
              <br />

            </form>

            {/* Login Button, submits login info */}
            <button type="button" onClick={login}>Login</button>


            {/* Sign Up Button, switches to sign up page */}
            <Link to={"/signUp"}>
              <button type="button">Sign Up</button>
            </Link>
            {/* <div style={{ backgroundImage: "url(/KUBU.png)" }}>
          hello world
        </div> */}

          </div>

        </div>



      </>);
}