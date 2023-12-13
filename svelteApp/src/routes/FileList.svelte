<script>
    import { onMount } from 'svelte'

    let listFiles = []

    onMount(async() => {
        const response = await fetch('http://localhost:11900/api/all')
        listFiles = await response.json()
        console.log(listFiles)
    })
</script>

{#each listFiles as file}
    <h3>{file.fileName}</h3>
    <p>{file.fileType}</p>
    <hr/>
    {:else}
    <p>Loading...</p>
{/each}