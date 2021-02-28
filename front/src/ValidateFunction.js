
export function ValidateFunction(setError, state) {
  const validName = () => {
    let isValid = true;
    setError(null);
    if (state.name.length < 1 || state.name.length > 100 || state.name === null) {
      setError("The character only can contains letters,numbers and spaces , maximum 100 characteres minimun 1");
      isValid = false;
    }
    return isValid;
  };
  const validadEspecialCharacters = () => {
    let isValidChar = true;
    setError(null);

    for (var i = 0; i < state.name.length; i++) {
      if (state.name.charAt(i) === '*') {
        setError("The character * is not valid");
        isValidChar = false;
      }
    }
    return isValidChar;
  };
  return { validName, validadEspecialCharacters };
}
