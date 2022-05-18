import './form.scss';
import {useRef} from "react";

export default function Form() {
  const name = useRef();
  const familyName = useRef();
  const email = useRef();
  const content = useRef();
  const subject = useRef();

  const submit = (e) => {
    e.preventDefault();
    console.log('submit');
    console.log(name);
    console.log(familyName);
    console.log(email);
    console.log(content);
  }

  return (
      <div className="formContainer" key="formContainer">
        <p className="title">Nous contacter</p>
        <form className="formulaire" onSubmit={submit}>
          <div className="field-wrap">
            <label className="label" htmlFor="familyName">
              Nom<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="familyName" required autoComplete="off"
                   ref={familyName}/>
          </div>
          <div className="field-wrap">
            <label className="label" htmlFor="firstname">
              Prénom<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="firstname" required autoComplete="off"
                   ref={name}/>
          </div>
          <div className="field-wrap">
            <label className="label" htmlFor="email">
              Em@il<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="email" required autoComplete="off"
                   ref={email}/>
          </div>
          <div className="field-wrap content">
            <label className="label" htmlFor="subject">
              Sujet<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <input type="text" name="subject" required autoComplete="off"
                   ref={subject}/>
          </div>
          <div className="field-wrap content">
            <label className="label">
              Veuillez expliquer votre situation<span className="req">*</span> :
            </label>
          </div>
          <div className="field-wrap">
            <textarea required autoComplete="off" ref={content}/>
          </div>
          <div className="field-wrap">
            <button type="submit"
                    className="btn styledButton validate">Envoyer
            </button>
          </div>
        </form>
      </div>
  );
}
