(ns your-own-detective.llm)

(comment
  ;(import org.bytedeco.onnxruntime.Env)
  ;(import org.bytedeco.onnxruntime.global.onnxruntime)
  ;(org.bytedeco.onnxruntime.Env. org.bytedeco.onnxruntime.global.onnxruntime/ORT_LOGGING_LEVEL_WARNING "test")
  (import de.kherud.llama.LlamaModel)
  (import de.kherud.llama.ModelParameters)
  (import de.kherud.llama.InferenceParameters)
  (de.kherud.llama.ModelParameters.)
  (def params (-> (ModelParameters.) (.setModel "models/gemma-3-1b-it-Q4_K_M.gguf")))
  
  

  (def prompt-text (str "<start_of_turn>user" "\n" (slurp "resources/my-prompt.txt") (slurp "resources/chapter-1.txt") "<end_of_turn>" "\n" "<start_of_turn>model" "\n" ))
  (with-open [model (de.kherud.llama.LlamaModel. params)]
    (let [my-prompt (-> (de.kherud.llama.InferenceParameters. prompt-text) (.setNPredict 1000) (.setGrammar (slurp "resources/grammar/evidence.gbnf")))]

      (.complete model my-prompt)))


  )