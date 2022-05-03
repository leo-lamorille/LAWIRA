import {useState} from "react";
import {Button} from "@mui/material";

export default function ({id, type, value}) {
  const [typeInput, setTypeInput] = useState(type);
  const [valueInput, setValueInput] = useState(value);

  function checkIfOptionModified() {
    return type !== typeInput || value !== valueInput;
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
      <Button color="error" variant="outlined">Supprimer</Button>
      <Button color="warning" variant="outlined"
              disabled={!checkIfOptionModified()}>Valider modification</Button>
    </td>
  </tr>
}
