package menezes.paulo.bomdebola.fragments.home;

/**
 * Created by Paulo Menezes on 21/07/2015.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.List;

import menezes.paulo.bomdebola.R;
import menezes.paulo.bomdebola.adapters.AdapterFragmentHomeNews;
import menezes.paulo.bomdebola.listeners.JsonResponse;
import menezes.paulo.bomdebola.util.DownloadJsonAsyncTask;
import menezes.paulo.bomdebola.util.JsonParser;
import menezes.paulo.bomdebola.util.URLs;

public class FragmentNews extends Fragment {
    public static String posts = "[{\"_id\":\"55b1a78369696d541cc5f3aa\",\"date\":\"2015/07/23 23:48:46\",\"idUser\":\"55b2a1cf5b45823042a5c5c6\",\"image\":\"BomDeBola_20150723_234842.jpg\",\"place\":\"\",\"text\":\"“Eu nunca imaginei que teria a chance que ganhei com o Recife Bom de Bola. Vou ter a oportunidade de me dedicar, profissionalmente, ao que mais gosto de fazer, jogar futebol”. O depoimento é de Marcelo Silva de França, 16 anos, um dos 11 jogares da seleção do torneio, que foram recebidos, nesta sexta-feira (26), pelo prefeito Geraldo Julio. Por iniciativa da Prefeitura do Recife, os jovens serão contratados pela Federação Pernambucana de Futebol (FPF) para treinar em clubes da 1ª Divisão do Campeonato Pernambucano. Os garotos foram selecionados entre 13 mil jogadores durante o torneio de futebol de várzea realizado pela PCR.\\n\\nOs garotos foram recebidos no próprio gabinete do prefeito. Na ocasião, Geraldo Julio destacou a importância do projeto para a cidade. “O Recife Bom de Bola é um campeonato que reúne milhares de pessoas e hoje nós recebemos os jovens que foram escolhidos como a ‘Seleção’. Eles terão a oportunidade de, quem sabe, entrar no futebol profissional e desenvolver a sua carreira. Porém, o mais importante: são atividades de lazer em todos os bairros da cidade. O Recife vive um outro momento de oportunidade para todas as pessoas”, afirmou o prefeito Geraldo Julio, depois de destacar a importância de conciliar os estudos com o treinamento.\\n\\nOs selecionados são da categoria Sub-17 e foram escolhidos por uma comissão especializada, formada pelos ex-jogadores Gena e Luciano Veloso, pelos jornalistas Wladmir Paulino, Lenivaldo Aragão, Álvaro Filho e Pedro Silva, e pelo técnico de futebol Hugo Benjamin. De acordo com o secretário de Esportes do Recife, George Braga, a iniciativa da PCR será aperfeiçoada a cada ano.  “Nós queremos melhorar cada vez mais. No próximo ano, vamos incluir a categoria Sub-13. Esse é, sobretudo, um grande projeto de inclusão. Queremos fazer do esporte a melhoria da qualidade de vida”, cravou.\\n\\nO Recife Bom de Bola de 2014 reuniu 13 mil jogadores e 550 equipes, nas categorias Aberto Masculino, Aberto Feminino, Sub 15, Sub 17 – já incluídas na edição do ano passado – e Veterano, a novidade deste ano, direcionada para pessoas com 40 anos ou mais.\\n\\nSELECIONADOS – Goleiro: Gustavo Vieira Cabral (Garoto Revelação); os zagueiros: Marlon Sena Silva (Real da Torre) e Matheus Marques (ASEEV); Lateral Direito: Philipe de Moura Canuto (Real da Torre); Lateral Esquerdo: João C. da Silva (ASEEV); Volante: Marcelo Silva de França (Garoto Revelação); Meias: Jeferson dos Santos Pereira (Inter Linha do Tiro) e Flávio de Lima (Família BH); Atacantes: Anderson Alexandre da Silva (Real da Torre), Cleber Henrique Pereira (Garoto Revelação) e Carlos Augusto A. da Silva (Família BH).\\n\\nFoto: Marcos Pastich/PCR\\n\",\"title\":\"Seleção do Recife Bom de Bola é recebida pelo prefeito\",\"type\":\"Public\",\"__v\":0},{\"_id\":\"55b19d6069696d541cc5f3a9\",\"date\":\"2015/07/23 23:05:33\",\"idUser\":\"55b2aa0d5b45823042a5c5c7\",\"place\":\"Recife\",\"text\":\"Eae pessoal, quem quer marcar para irmos ao jogo do Pernambucanos esse fim de semana?\",\"title\":\"Marcar encontro\",\"type\":\"Public\",\"__v\":0},{\"_id\":\"55b199620e75c8841072182f\",\"date\":\"2015/07/23 22:48:20\",\"idUser\":\"55b2a1cf5b45823042a5c5c6\",\"image\":\"BomDeBola_20150723_224819.jpg\",\"place\":\"\",\"text\":\"O jovem Gustavo Vieira Cabral já tinha desistido do sonho de tentar a vida como atleta profissional. O Recife Bom de Bola, campeonato de futebol de várzea promovido pela Prefeitura do Recife, o animou a voltar a atuar embaixo das traves, como goleiro do Garoto Revelação Futebol Clube, do bairro de Afogados. Deu certo: Gustavo foi escolhido para a seleção do torneio, na categoria Sub-17, e é um dos 11 jovens jogadores que serão contratados pela Federação Pernambucana de Futebol (FPF) e emprestados a clubes da 1ª Divisão do Campeonato Pernambucano de 2015.\\n\\nA seleção do Recife Bom de Bola foi recebida, na manhã desta segunda (22), pelo secretário municipal de Esportes, George Braga, que entregou um troféu a cada atleta. “O campeonato proporcionou a chance de todos aqui se destacarem. Vou encarar essa escolha como uma oportunidade de vencer na vida. A gente tem que se dedicar ao máximo”, declarou Gustavo. “Esse prêmio representa muito. Eram muitos times e muitos jogadores na disputa. Meu sonho é ser jogador e fazer teste num grande clube”, disse o atacante Anderson Alexandre, do Real da Torre (campeão do Sub-17).\\n\\nA escolha da Seleção foi feita por uma comissão especializada, formada pelos ex-jogadores Gena e Luciano Veloso, pelos jornalistas Wladmir Paulino, Lenivaldo Aragão, Álvaro Filho e Pedro Silva, e pelo técnico de futebol Hugo Benjamin. “O nível foi muito bom. Tivemos apenas três unanimidades na escolha. Como o futebol é um caminho difícil, uma carreira que acaba por volta dos 35 anos, lembramos que é fundamental continuar estudando”, orientou Benjamin, que coordenou o trabalho da comissão.\\n\\nO secretário George Braga ressaltou que, dos onze selecionados do ano passado, quatro continuam atuando nos clubes. “E dois deles vão disputar a Copa São Paulo de Futebol Júnior, pelo Sport e pelo Santa Cruz. Ficamos emocionados ao ver que o esporte funciona como ferramenta de inclusão social. É por isso que temos um carinho especial pelo Recife Bom de Bola. O importante é vocês entenderem que, trabalhando e correndo atrás de um sonho, é possível vencer na vida”, afirmou Braga.\\n\\nCONFIRA A SELEÇÃO DO RECIFE BOM DE BOLA 2014:\\n\\nGOLEIRO\\n\\n– Gustavo Vieira Cabral (Garoto Revelação)\\n\\nZAGUEIROS\\n\\n– Marlon Sena Silva (Real da Torre)\\n\\n– Matheus Marques (ASEEV)\\n\\nLATERAL DIREITO\\n\\n– Philipe de Moura Canuto (Real da Torre)\\n\\nLATERAL ESQUERDO\\n\\n– João C. da Silva (ASEEV)\\n\\nVOLANTE\\n\\n– Marcelo Silva de França (Garoto Revelação)\\n\\nMEIAS\\n\\n– Jeferson dos Santos Pereira (Inter Linha Do Tiro)\\n\\n– Flávio de Lima (Família BH)\\n\\nATACANTES\\n\\n– Anderson Alexandre da Silva (Real da Torre)\\n\\n– Cleber Henrique Pereira (Garoto Revelação)\\n\\n– Carlos Augusto A. da Silva (Família BH)\\n\\n\",\"title\":\"Seleção do Recife Bom de Bola assinará contrato para atuar em clubes da 1ª Divisão\",\"type\":\"Public\",\"__v\":0},{\"_id\":\"55b197570e75c8841072182e\",\"date\":\"2015/07/23 22:39:46\",\"idUser\":\"55b2aa0d5b45823042a5c5c7\",\"image\":\"BomDeBola_20150723_223944.jpg\",\"place\":\"\",\"text\":\"Fora de Forma e Grêmio Real da Mustardinha fizeram um jogo duríssimo, de muita marcação e poucas oportunidades de gol. Tão disputado que a decisão da categoria Aberto da Copa dos Campeões, etapa final do Recife Bom de Bola, só poderia mesmo sair nas penalidades. E o herói do título acabou sendo o goleiro Juninho, do Fora de Forma, que defendeu a cobrança do centroavante Amoroso e garantiu o título. A decisão aconteceu na manhã deste domingo (14), no campo da Vila da Sudene, situado no bairro do Ipsep.\\n\\nAmbas as equipes levaram um bom número de torcedores para acompanhar a decisão. A primeira cobrança coube a Colin, camisa 9 do Fora de Forma, que colocou sua equipe em vantagem. Amauri empatou para o Real; Parral marcou para o Fora de Forma e Pelado fez 2 x 2. Nego converteu, mas o chute de Amoroso parou nas mãos do goleiro Juninho. O Fora de Forma abriu vantagem com o gol de Rafael e Pintado manteve vivas as esperanças do Real, até que Dero converteu o pênalti decisivo: 5 x 3, Fora de Forma campeão.\\n\\n“No jogo passado, um de nossos diretores me disse que o Amoroso costuma bater cruzado. Só tive que manter a calma e esperar o chute. Agora é só comemorar! Graças a Deus, conquistamos os títulos da RPA e da Copa dos Campeões”, disse Juninho.\\n\\nPresidente e treinador da agremiação, Walnez de Melo destacou o “grande esforço” que resultou nas conquistas do Fora de Forma. “Tivemos um ano 100%, com muita ajuda dos patrocinadores e da diretoria. Nosso time foi fundado em agosto de 2002 e já venceu três etapas regionais e, agora, a Copa dos Campeões”, informou. A equipe é do bairro de Santo Amaro.\\n\\nO secretário-executivo de Esportes do Recife, Warlindo Carneiro, entrou as medalhas e troféus para as duas equipes. “Tivemos mais um campeonato de grande sucesso, reunindo centenas de equipes de toda a cidade. Estamos muito felizes”, avaliou.\\n\\nFoto: Lú Streithorst/PCR\",\"title\":\"Fora de Forma derrota o Grêmio Real da Mustardinha e leva o título do Aberto\",\"type\":\"Public\",\"__v\":0}]";
    public static List<JSONObject> sObjectList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.fragment_home_news, container, false);

        sObjectList = JsonParser.getObjects(posts);

        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(final RecyclerView recyclerView) {
        if (sObjectList == null) {
            final ProgressDialog mDialog = ProgressDialog.show(getActivity(), "", getResources().getString(R.string.msg_loading, true));

            new DownloadJsonAsyncTask(new JsonResponse() {
                @Override
                public void response(List<JSONObject> objectList) {
                    sObjectList = objectList;

                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(new AdapterFragmentHomeNews(getActivity(), objectList, recyclerView));

                    mDialog.dismiss();
                }
            }).execute(URLs.API_POST);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(new AdapterFragmentHomeNews(getActivity(), sObjectList, recyclerView));
        }
    }
}