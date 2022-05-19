import {useState} from "react";
import {Button} from "@mui/material";
import {useDispatch, useSelector} from "react-redux";
import {userSlice} from "../slices/userSlice";
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";

export default function ({attributeId, id, type, value, refresh, showAlert}) {
  const userAction = userSlice.actions;
  const dispatch = useDispatch();
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
  const navigate = useNavigate();

  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [typeInput, setTypeInput] = useState(type);
  const [valueInput, setValueInput] = useState(value);

  const logout = () => {
    dispatch(userAction.logout(undefined));
    removeCookie('token', {path: '/'});
    navigate('/home');
  }

  function checkIfOptionModified() {
    return type !== typeInput || value !== valueInput;
  }

  function deleteOption() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken)
    fetch(`http://localhost:8080/admin/attributes/${attributeId}/options/${id}`,
        {
          headers, method: 'DELETE'
        })
    .then((res) => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refresh();
        showAlert("Option supprimée")
      }
    });
  }

  function updateOption() {
    let headers = new Headers()
    headers.append("Authorization", 'Bearer ' + userToken);
    headers.append("Content-Type", 'application/json');
    const body = JSON.stringify({
      type: typeInput, value: valueInput
    })
    fetch(`http://localhost:8080/admin/attributes/${attributeId}/options/${id}`,
        {
          headers, method: 'PUT', body
        })
    .then((res) => {
      if (res.status === 403) {
        logout();
        throw "You are not authorized to do this action"
      } else if (res.status === 200) {
        refresh();
        showAlert("Option mise à jour")
      }
    });
  }

  return <tr>
    <td>
      <select value={typeInput} onChange={(e) => setTypeInput(e.target.value)}>
        <option value="COLOR">Couleur</option>
        <option value="TEXT">Texte</option>
      </select>
    </td>
    <td><input type="text" value={valueInput}
               onChange={(e) => setValueInput(e.target.value)}/></td>
    <td>
      <button className="btn styledButton" type="button"
              onClick={deleteOption}>Supprimer</button>
      <button className="btn styledButton" type="button"
              disabled={!checkIfOptionModified()} onClick={updateOption}>Valider modification</button>
    </td>
  </tr>
}
