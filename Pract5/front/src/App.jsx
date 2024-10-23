import { useCallback, useEffect, useState } from 'react';
import { $axios, SERVER_URL } from './http.js';

function App() {
	const [ filesName, setFilesName ] = useState([]);
	const [ refresh, setRefresh ] = useState(0);
	const [ file, setFile ] = useState('');

	useEffect(() => {
		$axios.get('/files').then(({ data }) => setFilesName(data));
	}, [ refresh ]);

	const onSubmitFile = useCallback(async (e) => {
		e.preventDefault();

		if (file === '') {
			return;
		}

		const formData = new FormData();
		formData.append('file', file);

		try {
			await $axios.post('/files', formData);
			setRefresh(prev => prev + 1);
			setFile('');
		} catch (e) {
			console.log(e);
		}
	}, [ file ]);

	const onFileChange = (e) => {
		setFile(e.target.files[0]);
	};

	return (
		<div>
			<form>
				<input type={'file'} onChange={onFileChange}/>
				<input type={'submit'} onClick={onSubmitFile}/>
			</form>
			<div style={{ marginTop: '50px' }}>
				{filesName.map((name) => {
					return (
						<div key={name}>
							<a href={SERVER_URL + name} download={SERVER_URL + name}>{name}</a>
						</div>
					);
				})}
			</div>
		</div>
	);
}

export default App;
