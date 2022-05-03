import {useState} from "react";
import {Button} from "@mui/material";
import {useSelector} from "react-redux";

export default function ({attributeId, id, type, value, refresh}) {
  const userToken = useSelector(state => state.user.jwt);
  const userRole = useSelector(state => state.user.role);
  const [typeInput, setTypeInput] = useState(type);
  const [valueInput, setValueInput] = useState(value);

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
    .then(() => {
      refresh();
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
    .then(() => {
      refresh();
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
      <Button color="error" variant="outlined" type="button"
              onClick={deleteOption}>Supprimer</Button>
      <Button color="warning" variant="outlined" type="button"
              disabled={!checkIfOptionModified()} onClick={updateOption}>Valider modification</Button>
    </td>
  </tr>
}
