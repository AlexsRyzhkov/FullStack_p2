import {useCallback, useEffect, useState} from 'react'
import {$axios} from "./http.js";

function App() {
  const [filesData, setFilesData] = useState([])

  useEffect(() => {
    $axios.get('/files').then((data)=>console.log(data))
  }, []);

  useEffect(() => {
    const intervalID = setInterval(()=>{

    },1000);

    return () => clearInterval(intervalID)
  }, []);

  const onSubmitFile = useCallback((e)=>{
    e.preventDefault()
  },[])

  return (
    <div>
      <form onSubmit={onSubmitFile}>
        <input type={'file'}/>
        <input type={'submit'}/>
      </form>
      <div>
        {filesData.map((fileData)=>{
          return (
              <div>
                <a href={fileData.link} download={fileData.name}>{fileData.name}</a>
              </div>
          )
        })}
      </div>
    </div>
  )
}

export default App
